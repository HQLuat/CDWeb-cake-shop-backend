package vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateItemRequest(
        @NotNull(message = "quantity không được để trống")
        @Min(value = 1, message = "Số lượng phải ít nhất là 1")
        Integer quantity
) {}
