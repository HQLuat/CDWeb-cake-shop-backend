package vn.edu.hcmuaf.fit.cakeshop.modules.order.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.CreateOrderRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
    OrderResponse confirmReceived(Long id);

    Page<OrderResponse> getMyOrders(String keyword, OrderStatus orderStatus, PaymentStatus paymentStatus, int page, int size);
    Page<OrderResponse> getAllOrders(String keyword, OrderStatus orderStatus, PaymentStatus paymentStatus, int page, int size);
}
