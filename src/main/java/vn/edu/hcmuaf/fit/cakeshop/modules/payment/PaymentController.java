package vn.edu.hcmuaf.fit.cakeshop.modules.payment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentCreateRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.PaymentService;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * POST /api/payment/vnpay/create
     * Tạo liên kết thanh toán VNPay cho đơn hàng
     */
    @PostMapping("/vnpay/create")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<PaymentResponse>> createVNPayPayment(
            @Valid @RequestBody PaymentCreateRequest request,
            HttpServletRequest servletRequest
    ) {
        String ipAddress = getClientIp(servletRequest);
        PaymentResponse response = paymentService.createVNPayPayment(request, ipAddress);
        return ResponseEntity.ok(ApiResponse.success("Tạo liên kết thanh toán thành công", response));
    }

    /**
     * GET /api/payment/vnpay/return
     * Nhận callback redirect từ VNPay sau khi thanh toán xong
     */
    @GetMapping("/vnpay/return")
    public ResponseEntity<Void> handleVNPayReturn(
            @RequestParam Map<String, String> params
    ) {
        String redirectUrl = paymentService.handleVNPayCallback(params);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .build();
    }

    // Helper: Lấy địa chỉ IP thực tế của Client
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Trường hợp qua nhiều proxy, lấy IP đầu tiên
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
