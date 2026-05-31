package vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cakeshop.config.VNPayConfig;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.VNPayService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {

    private final VNPayConfig config;

    @Override
    public String createPaymentUrl(String txnRef, BigDecimal amount, String ipAddress) {
        // Dùng timezone GMT+7 đúng theo yêu cầu VNPay (giống code mẫu)
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        // VNPay yêu cầu số tiền nhân 100 (đơn vị: đồng, không có phần thập phân)
        String amountStr = String.valueOf(amount.multiply(BigDecimal.valueOf(100)).toBigInteger());

        // Trích xuất orderId từ txnRef dạng "orderId_timestamp"
        String orderId = txnRef.split("_")[0];

        // Dùng TreeMap để các tham số tự động sắp xếp theo alphabet (đúng yêu cầu VNPay)
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", config.getTmnCode());
        params.put("vnp_Amount", amountStr);
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Thanh toan don hang:" + orderId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", config.getReturnUrl());
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_CreateDate", vnp_CreateDate);
        params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build hashData và query theo đúng logic code mẫu VNPay:
        //   hashData : key KHÔNG encode, value encode US_ASCII
        //   query    : key encode,       value encode US_ASCII
        //   '&' chỉ append khi còn phần tử tiếp theo (tránh trailing &)
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            String key   = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                // hashData: key nguyên bản, value encode
                hashData.append(key)
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));

                // query: cả key và value đều encode
                query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Tính HMAC-SHA512 trên hashData
        String secureHash = hmacSHA512(config.getHashSecret(), hashData.toString());

        // Ghép secureHash vào sau query string (không encode hash)
        query.append("&vnp_SecureHash=").append(secureHash);

        return config.getUrl() + "?" + query;
    }

    @Override
    public boolean verifySignature(Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (vnp_SecureHash == null || vnp_SecureHash.isEmpty()) {
            return false;
        }

        // Loại bỏ vnp_SecureHash và vnp_SecureHashType trước khi tính lại hash
        Map<String, String> sortedParams = new TreeMap<>(params);
        sortedParams.remove("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHashType");

        // Build hashData theo đúng cách VNPay: key KHÔNG encode, value encode US_ASCII
        // '&' chỉ append khi còn phần tử tiếp theo
        StringBuilder hashData = new StringBuilder();
        Iterator<Map.Entry<String, String>> itr = sortedParams.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            String key   = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                hashData.append(key)
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }

        String expectedHash = hmacSHA512(config.getHashSecret(), hashData.toString());
        return expectedHash.equalsIgnoreCase(vnp_SecureHash);
    }

    // HMAC-SHA512 — key dùng UTF-8, data dùng UTF-8 (giống Config.java mẫu)
    private String hmacSHA512(String key, String data) {
        try {
            if (key == null || data == null) {
                return "";
            }
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}