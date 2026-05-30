package vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums;

public enum PaymentStatus {
    UNPAID,      // Chưa thanh toán
    PAID,        // Đã thanh toán
    REFUNDED,    // Đã hoàn tiền
    FAILED       // Thanh toán thất bại
}
