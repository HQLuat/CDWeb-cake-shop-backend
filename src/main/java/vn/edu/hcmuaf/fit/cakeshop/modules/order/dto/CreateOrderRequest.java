package vn.edu.hcmuaf.fit.cakeshop.modules.order.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
        @NotBlank(message = "Địa chỉ giao hàng không được để trống")
        String shippingAddress,
        
        String note
) {
}
