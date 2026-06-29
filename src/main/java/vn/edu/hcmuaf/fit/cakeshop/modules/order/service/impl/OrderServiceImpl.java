package vn.edu.hcmuaf.fit.cakeshop.modules.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.auth.domain.repository.AuthRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.Cart;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.repository.CartRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.Order;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.OrderItem;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.repository.OrderRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.CreateOrderRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.service.OrderService;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthRepository userRepository;

    // Helper: lấy User đang đăng nhập từ SecurityContext
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + username));
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        User user = getCurrentUser();
        
        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng của bạn"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng của bạn đang trống, không thể tạo đơn hàng");
        }

        // Tạo đối tượng Order
        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.shippingAddress())
                .note(request.note())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)
                .totalAmount(BigDecimal.ZERO) // Sẽ tính toán ở bước dưới
                .build();

        // Chuyển CartItem thành OrderItem
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            BigDecimal price = cartItem.getProduct().getCurrentPrice() != null
                    ? cartItem.getProduct().getCurrentPrice()
                    : cartItem.getProduct().getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            return OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(price)
                    .subtotal(subtotal)
                    .build();
        }).collect(Collectors.toList());

        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        // Lưu Order (sẽ cascade lưu cả OrderItems)
        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt thành công
        cart.getItems().clear();
        cartRepository.save(cart);

        return OrderResponse.fromEntity(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(String keyword, OrderStatus orderStatus, PaymentStatus paymentStatus, int page, int size) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        return orderRepository.findPageByUserId(user.getId(), keyword, orderStatus, paymentStatus, pageable)
                .map(OrderResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        User user = getCurrentUser();
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + id));

        // Nếu không phải ADMIN và không phải chủ đơn hàng thì chặn
        if (!user.getRole().equals(UserRole.ADMIN) && !order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền xem đơn hàng này");
        }

        return OrderResponse.fromEntity(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(String keyword, OrderStatus orderStatus, PaymentStatus paymentStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        return orderRepository.findAllWithFilters(keyword, orderStatus, paymentStatus, pageable)
                .map(OrderResponse::fromEntity);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + id));

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        
        // Nếu hủy đơn hàng, có thể chuyển trạng thái thanh toán thành FAILED nếu chưa thanh toán
        if (status == OrderStatus.CANCELLED && order.getPaymentStatus() == PaymentStatus.UNPAID) {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse confirmReceived(Long id) {
        User user = getCurrentUser();
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với id: " + id));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền xác nhận đơn hàng này");
        }

        if (order.getOrderStatus() != OrderStatus.SHIPPING) {
            throw new RuntimeException("Chỉ có thể xác nhận đơn hàng đang được giao");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder);
    }
}
