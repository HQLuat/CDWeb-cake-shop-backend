package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.service;

import vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.dto.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {
    SummaryStatsDTO getSummary(LocalDate from, LocalDate to);
    List<RevenueByDateDTO> getRevenue(LocalDate from, LocalDate to);
    List<OrderStatusStatsDTO> getOrderStatus(LocalDate from, LocalDate to);
    List<TopProductDTO> getTopProducts(LocalDate from, LocalDate to);
    List<OrderResponse> getRecentOrders();
}
