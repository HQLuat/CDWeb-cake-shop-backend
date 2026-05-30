package vn.edu.hcmuaf.fit.cakeshop.modules.order.service;

import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.CreateOrderRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getMyOrders();
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
}
