package vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddItemRequest(
        @NotNull(message = "productId không được để trống")
        Long productId,

        @NotNull(message = "quantity không được để trống")
        @Min(value = 1, message = "Số lượng phải ít nhất là 1")
        Integer quantity
) {}
