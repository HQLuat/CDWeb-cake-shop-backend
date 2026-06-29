package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto;

public record OrderStatusStatsDTO(
        String status,
        long count
) {}
