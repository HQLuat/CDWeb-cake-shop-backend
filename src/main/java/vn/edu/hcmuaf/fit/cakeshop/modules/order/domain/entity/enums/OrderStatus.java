package vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums;

public enum OrderStatus {
    PENDING,      // Chờ xác nhận
    CONFIRMED,    // Đã xác nhận (đã thanh toán hoặc COD được chấp nhận)
    SHIPPING,     // Đang giao hàng
    COMPLETED,    // Đã hoàn thành
    CANCELLED     // Đã hủy
}
