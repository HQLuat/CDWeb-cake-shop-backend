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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {

    private final VNPayConfig config;

    @Override
    public String createPaymentUrl(String txnRef, BigDecimal amount, String ipAddress) {
        LocalDateTime createDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        
        String vnp_CreateDate = createDate.format(formatter);
        String vnp_ExpireDate = createDate.plusMinutes(15).format(formatter);

        // Nhân số tiền với 100 theo yêu cầu của VNPay (đơn vị: đồng)
        BigDecimal vnpAmount = amount.multiply(BigDecimal.valueOf(100));
        String amountStr = String.valueOf(vnpAmount.toBigInteger());

        // Sử dụng TreeMap để các tham số được sắp xếp theo thứ tự alphabet
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", config.getTmnCode());
        params.put("vnp_Amount", amountStr);
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        
        // Trích xuất mã đơn hàng từ txnRef (dạng orderId_timestamp)
        String orderId = txnRef.split("_")[0];
        params.put("vnp_OrderInfo", "Thanh toan don hang #" + orderId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", config.getReturnUrl());
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_CreateDate", vnp_CreateDate);
        params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi Hash Data và Query String
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            String key = entry.getKey();
            String value = entry.getValue();
            
            if (value != null && !value.isEmpty()) {
                // Build Hash Data
                hashData.append(key).append('=').append(encode(value));
                
                // Build Query String
                query.append(encode(key)).append('=').append(encode(value));
                
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Tính toán chữ ký bảo mật HMAC-SHA512
        String secureHash = hmacSHA512(config.getHashSecret(), hashData.toString());
        
        // Thêm chữ ký vào Query String
        query.append("&vnp_SecureHash=").append(secureHash);

        return config.getUrl() + "?" + query.toString();
    }

    @Override
    public boolean verifySignature(Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return false;
        }

        // Sắp xếp các tham số nhận được loại trừ vnp_SecureHash và vnp_SecureHashType
        Map<String, String> sortedParams = new TreeMap<>(params);
        sortedParams.remove("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHashType");

        StringBuilder hashData = new StringBuilder();
        Iterator<Map.Entry<String, String>> itr = sortedParams.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                hashData.append(key).append('=').append(encode(value));
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }

        // Đảm bảo không có dấu & thừa ở cuối chuỗi do bộ lọc phần tử trống
        String rawHash = hashData.toString();
        if (rawHash.endsWith("&")) {
            rawHash = rawHash.substring(0, rawHash.length() - 1);
        }

        String expectedHash = hmacSHA512(config.getHashSecret(), rawHash);
        return expectedHash.equalsIgnoreCase(vnp_SecureHash);
    }

    // Helper: Mã hóa URL theo chuẩn mã hóa của VNPay (thay thế dấu cộng bằng %20)
    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");
        } catch (Exception e) {
            return "";
        }
    }

    // Helper: Tính toán chuỗi mã hóa HMAC-SHA512
    private String hmacSHA512(String key, String data) {
        try {
            if (key == null || data == null) {
                return "";
            }
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
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
