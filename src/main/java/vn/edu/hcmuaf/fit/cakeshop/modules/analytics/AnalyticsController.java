package vn.edu.hcmuaf.fit.cakeshop.modules.analytics;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.analytics.service.AnalyticsService;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Admin analytics & statistics endpoints")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * GET /api/admin/analytics/summary?from=yyyy-MM-dd&to=yyyy-MM-dd
     * Trả về 4 thẻ tóm tắt + % thay đổi so với kỳ trước
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<SummaryStatsDTO>> getSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy thống kê tóm tắt thành công",
                analyticsService.getSummary(from, to)
        ));
    }

    /**
     * GET /api/admin/analytics/revenue?from=yyyy-MM-dd&to=yyyy-MM-dd
     * Trả về doanh thu theo từng ngày (chỉ đơn PAID)
     */
    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<List<RevenueByDateDTO>>> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy doanh thu theo ngày thành công",
                analyticsService.getRevenue(from, to)
        ));
    }

    /**
     * GET /api/admin/analytics/order-status?from=yyyy-MM-dd&to=yyyy-MM-dd
     * Trả về phân bổ đơn hàng theo trạng thái
     */
    @GetMapping("/order-status")
    public ResponseEntity<ApiResponse<List<OrderStatusStatsDTO>>> getOrderStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy thống kê trạng thái đơn hàng thành công",
                analyticsService.getOrderStatus(from, to)
        ));
    }

    /**
     * GET /api/admin/analytics/top-products?from=yyyy-MM-dd&to=yyyy-MM-dd
     * Trả về top 10 sản phẩm bán chạy nhất
     */
    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse<List<TopProductDTO>>> getTopProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy top sản phẩm bán chạy thành công",
                analyticsService.getTopProducts(from, to)
        ));
    }

    /**
     * GET /api/admin/analytics/recent-orders
     * Trả về 10 đơn hàng mới nhất
     */
    @GetMapping("/recent-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getRecentOrders() {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy đơn hàng mới nhất thành công",
                analyticsService.getRecentOrders()
        ));
    }
}
