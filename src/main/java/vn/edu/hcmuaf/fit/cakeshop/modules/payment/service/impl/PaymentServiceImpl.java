package vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.auth.domain.repository.AuthRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.Order;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.repository.OrderRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.domain.entity.Payment;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.domain.entity.enums.PaymentTransactionStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.domain.repository.PaymentRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentCreateRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.PaymentService;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.service.VNPayService;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final AuthRepository userRepository;
    private final VNPayService vnpayService;
    private final ObjectMapper objectMapper;

    // Helper: lấy User đang đăng nhập từ SecurityContext
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + username));
    }

    @Override
    @Transactional
    public PaymentResponse createVNPayPayment(PaymentCreateRequest request, String ipAddress) {
        User user = getCurrentUser();

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + request.orderId()));

        // Kiểm tra quyền sở hữu đơn hàng
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền thực hiện thanh toán cho đơn hàng này");
        }

        // Kiểm tra trạng thái thanh toán
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Đơn hàng này đã được thanh toán thành công trước đó");
        }

        // Tạo mã giao dịch gửi đi duy nhất dựa trên thời gian để tránh bị trùng lặp mã đơn bên VNPay
        String vnpTxnRef = order.getId() + "_" + System.currentTimeMillis();

        // Tạo URL thanh toán
        String paymentUrl = vnpayService.createPaymentUrl(vnpTxnRef, order.getTotalAmount(), ipAddress);

        // Lưu thông tin giao dịch PENDING vào database
        Payment payment = Payment.builder()
                .order(order)
                .vnpTxnRef(vnpTxnRef)
                .amount(order.getTotalAmount())
                .status(PaymentTransactionStatus.PENDING)
                .build();
        paymentRepository.save(payment);

        return new PaymentResponse(paymentUrl);
    }

    @Override
    @Transactional
    public String handleVNPayCallback(Map<String, String> params) {
        // 1. Xác minh chữ ký bảo mật từ VNPay
        boolean isValidSignature = vnpayService.verifySignature(params);
        if (!isValidSignature) {
            throw new RuntimeException("Xác thực chữ ký bảo mật từ VNPay thất bại");
        }

        String vnpTxnRef = params.get("vnp_TxnRef");
        String vnpResponseCode = params.get("vnp_ResponseCode");
        String vnpAmount = params.get("vnp_Amount");
        String vnpTransactionNo = params.get("vnp_TransactionNo");
        String vnpBankCode = params.get("vnp_BankCode");
        String vnpCardType = params.get("vnp_CardType");

        // 2. Tìm kiếm bản ghi thanh toán tương ứng
        Payment payment = paymentRepository.findByVnpTxnRef(vnpTxnRef)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giao dịch với mã tham chiếu: " + vnpTxnRef));

        Order order = payment.getOrder();

        // 3. Kiểm tra tính Idempotent (Nếu giao dịch đã xử lý xong từ trước thì bỏ qua bước cập nhật trạng thái)
        if (payment.getStatus() != PaymentTransactionStatus.PENDING) {
            boolean isSuccess = payment.getStatus() == PaymentTransactionStatus.SUCCESS;
            return "http://localhost:5173/payment/result?status=" + (isSuccess ? "success" : "failed") + "&orderId=" + order.getId();
        }

        // 4. Đối chiếu số tiền thanh toán (VNPay nhân số tiền với 100)
        BigDecimal totalAmountVnPay = new BigDecimal(vnpAmount).divide(BigDecimal.valueOf(100));
        if (totalAmountVnPay.compareTo(order.getTotalAmount()) != 0) {
            // Cập nhật trạng thái giao dịch và đơn hàng thất bại do không khớp tiền
            payment.setStatus(PaymentTransactionStatus.FAILED);
            payment.setRawResponse(toJsonString(params));
            paymentRepository.save(payment);

            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);

            throw new RuntimeException("Số tiền thanh toán trên VNPay không khớp với tổng tiền đơn hàng");
        }

        // 5. Cập nhật chi tiết lịch sử giao dịch thanh toán
        payment.setVnpTransactionNo(vnpTransactionNo);
        payment.setBankCode(vnpBankCode);
        payment.setPaymentMethod(vnpCardType);
        payment.setRawResponse(toJsonString(params));

        boolean isSuccess = "00".equals(vnpResponseCode);
        if (isSuccess) {
            payment.setStatus(PaymentTransactionStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());

            order.setPaymentStatus(PaymentStatus.PAID);
            order.setOrderStatus(OrderStatus.CONFIRMED); // Tự động xác nhận đơn hàng khi đã thanh toán thành công
        } else {
            payment.setStatus(PaymentTransactionStatus.FAILED);
            order.setPaymentStatus(PaymentStatus.FAILED);
        }

        order.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
        orderRepository.save(order);

        // Chuyển hướng người dùng về trang Frontend tương ứng
        return "http://localhost:5173/payment/result?status=" + (isSuccess ? "success" : "failed") + "&orderId=" + order.getId();
    }

    private String toJsonString(Map<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return map.toString();
        }
    }
}
