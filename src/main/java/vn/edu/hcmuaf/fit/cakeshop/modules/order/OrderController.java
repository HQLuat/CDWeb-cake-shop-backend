package vn.edu.hcmuaf.fit.cakeshop.modules.order;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.CreateOrderRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/orders
     * Tạo đơn hàng từ giỏ hàng hiện tại
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Tạo đơn hàng thành công",
                orderService.createOrder(request)
        ));
    }

    /**
     * GET /api/orders
     * Lấy danh sách đơn hàng của user hiện tại
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) PaymentStatus paymentStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy danh sách đơn hàng thành công",
                orderService.getMyOrders(keyword, orderStatus, paymentStatus, page, size)
        ));
    }

    /**
     * GET /api/orders/{id}
     * Chi tiết đơn hàng (chỉ cho phép chủ đơn hàng hoặc ADMIN)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy chi tiết đơn hàng thành công",
                orderService.getOrderById(id)
        ));
    }

    /**
     * GET /api/orders/admin
     * Quản lý tất cả đơn hàng (ADMIN)
     */
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) PaymentStatus paymentStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy toàn bộ danh sách đơn hàng thành công",
                orderService.getAllOrders(keyword, orderStatus, paymentStatus, page, size)
        ));
    }

    /**
     * PUT /api/orders/{id}/status
     * Cập nhật trạng thái đơn hàng (ADMIN)
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Cập nhật trạng thái đơn hàng thành công",
                orderService.updateOrderStatus(id, status)
        ));
    }
}
