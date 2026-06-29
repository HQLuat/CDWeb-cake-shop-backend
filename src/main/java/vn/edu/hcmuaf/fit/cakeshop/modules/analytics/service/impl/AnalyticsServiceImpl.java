package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.analytics.service.AnalyticsService;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.repository.OrderRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // ─── SUMMARY ──────────────────────────────────────────────────────────────

    @Override
    public SummaryStatsDTO getSummary(LocalDate from, LocalDate to) {
        LocalDateTime fromDt = from.atStartOfDay();
        LocalDateTime toDt = to.atTime(LocalTime.MAX);

        // Tính khoảng thời gian kỳ trước (cùng độ dài)
        long days = from.until(to).getDays() + 1;
        LocalDateTime prevFromDt = fromDt.minusDays(days);
        LocalDateTime prevToDt = toDt.minusDays(days);

        // Kỳ hiện tại
        long totalOrders = orderRepository.countByCreatedAtBetween(fromDt, toDt);
        BigDecimal totalRevenue = orderRepository.sumRevenueByPaidStatus(fromDt, toDt);
        long pendingOrders = orderRepository.countByOrderStatusAndCreatedAtBetween(OrderStatus.PENDING, fromDt, toDt);
        long newCustomers = userRepository.countByCreatedAtBetween(fromDt, toDt);

        // Kỳ trước
        long prevOrders = orderRepository.countByCreatedAtBetween(prevFromDt, prevToDt);
        BigDecimal prevRevenue = orderRepository.sumRevenueByPaidStatus(prevFromDt, prevToDt);
        long prevPending = orderRepository.countByOrderStatusAndCreatedAtBetween(OrderStatus.PENDING, prevFromDt, prevToDt);
        long prevCustomers = userRepository.countByCreatedAtBetween(prevFromDt, prevToDt);

        return new SummaryStatsDTO(
                totalOrders,
                totalRevenue,
                pendingOrders,
                newCustomers,
                calcChange(totalOrders, prevOrders),
                calcChange(totalRevenue, prevRevenue),
                calcChange(pendingOrders, prevPending),
                calcChange(newCustomers, prevCustomers)
        );
    }

    // ─── REVENUE BY DATE ──────────────────────────────────────────────────────

    @Override
    public List<RevenueByDateDTO> getRevenue(LocalDate from, LocalDate to) {
        LocalDateTime fromDt = from.atStartOfDay();
        LocalDateTime toDt = to.atTime(LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return orderRepository.revenueGroupByDate(fromDt, toDt)
                .stream()
                .map(row -> {
                    // row[0] is java.sql.Date or LocalDate depending on the DB
                    String date = row[0].toString();
                    BigDecimal revenue = (BigDecimal) row[1];
                    return new RevenueByDateDTO(date, revenue);
                })
                .toList();
    }

    // ─── ORDER STATUS STATS ───────────────────────────────────────────────────

    @Override
    public List<OrderStatusStatsDTO> getOrderStatus(LocalDate from, LocalDate to) {
        LocalDateTime fromDt = from.atStartOfDay();
        LocalDateTime toDt = to.atTime(LocalTime.MAX);

        return orderRepository.countByStatusInRange(fromDt, toDt)
                .stream()
                .map(row -> new OrderStatusStatsDTO(
                        ((OrderStatus) row[0]).name(),
                        (Long) row[1]
                ))
                .toList();
    }

    // ─── TOP PRODUCTS ─────────────────────────────────────────────────────────

    @Override
    public List<TopProductDTO> getTopProducts(LocalDate from, LocalDate to) {
        LocalDateTime fromDt = from.atStartOfDay();
        LocalDateTime toDt = to.atTime(LocalTime.MAX);

        return orderRepository.topProductsByQuantity(fromDt, toDt, PageRequest.of(0, 10))
                .stream()
                .map(row -> new TopProductDTO(
                        (String) row[0],
                        (Long) row[1],
                        (BigDecimal) row[2]
                ))
                .toList();
    }

    // ─── RECENT ORDERS ────────────────────────────────────────────────────────

    @Override
    public List<OrderResponse> getRecentOrders() {
        return orderRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────

    private double calcChange(long current, long previous) {
        if (previous == 0) return current > 0 ? 100.0 : 0.0;
        return BigDecimal.valueOf((double) (current - previous) / previous * 100)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double calcChange(BigDecimal current, BigDecimal previous) {
        if (previous == null || previous.compareTo(BigDecimal.ZERO) == 0)
            return current != null && current.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
