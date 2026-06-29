package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto;

import java.math.BigDecimal;

public record SummaryStatsDTO(
        long totalOrders,
        BigDecimal totalRevenue,
        long pendingOrders,
        long newCustomers,
        double orderChange,
        double revenueChange,
        double pendingChange,
        double customerChange
) {}
