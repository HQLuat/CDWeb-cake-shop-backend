package vn.edu.hcmuaf.fit.cakeshop.modules.analytics.dto;

import java.math.BigDecimal;

public record TopProductDTO(
        String productName,
        long quantitySold,
        BigDecimal revenue
) {}
