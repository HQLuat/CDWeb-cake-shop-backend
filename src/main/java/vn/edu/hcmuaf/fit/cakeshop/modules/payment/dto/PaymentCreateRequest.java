package vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentCreateRequest(
        @NotNull(message = "Mã đơn hàng không được để trống")
        Long orderId
) {
}
