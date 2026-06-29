package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto;

import java.math.BigDecimal;

public record RevenueByDateDTO(
        String date,
        BigDecimal revenue
) {}
