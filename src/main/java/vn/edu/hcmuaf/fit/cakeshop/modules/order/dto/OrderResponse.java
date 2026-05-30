package vn.edu.hcmuaf.fit.cakeshop.modules.order.dto;

import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(
        Long id,
        Long userId,
        String username,
        BigDecimal totalAmount,
        String orderStatus,
        String paymentStatus,
        String shippingAddress,
        String note,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<OrderItemResponse> items
) {
    public static OrderResponse fromEntity(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems() == null
                ? Collections.emptyList()
                : order.getItems().stream()
                .map(OrderItemResponse::fromEntity)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getUsername(),
                order.getTotalAmount(),
                order.getOrderStatus().name(),
                order.getPaymentStatus().name(),
                order.getShippingAddress(),
                order.getNote(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                itemResponses
        );
    }
}
