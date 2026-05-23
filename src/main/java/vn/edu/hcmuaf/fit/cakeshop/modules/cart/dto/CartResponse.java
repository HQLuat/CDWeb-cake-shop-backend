package vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto;

import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.Cart;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record CartResponse(
        Long cartId,
        List<CartItemResponse> items,
        Integer totalItems,       // tổng số loại sản phẩm
        Integer totalQuantity,    // tổng số lượng tất cả sản phẩm
        BigDecimal totalAmount    // tổng tiền
) {
    public static CartResponse fromEntity(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems() == null
                ? Collections.emptyList()
                : cart.getItems().stream()
                .map(CartItemResponse::fromEntity)
                .collect(Collectors.toList());

        int totalQuantity = itemResponses.stream()
                .mapToInt(CartItemResponse::quantity)
                .sum();

        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                itemResponses,
                itemResponses.size(),
                totalQuantity,
                totalAmount
        );
    }
}
