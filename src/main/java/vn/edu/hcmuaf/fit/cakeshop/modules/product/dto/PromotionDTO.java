package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import java.math.BigDecimal;
import java.util.List;

public class PromotionDTO {
    // Request: áp dụng giảm giá 1 sản phẩm
    public record ApplyPromotionRequest(
            Long productId,
            Integer discountPercent   // 1–99
    ) {}

    // Request: áp dụng hàng loạt
    public record BulkApplyPromotionRequest(
            List<Long> productIds,
            Integer discountPercent
    ) {}

    // Response trả về frontend
    public record PromotionProductResponse(
            Long id,
            String name,
            String description,
            String collection,
            BigDecimal price,
            BigDecimal currentPrice,
            Double averageRating,
            Integer totalReviews,
            List<String> imageUrls,
            Integer discountPercent,
            BigDecimal discountedPrice
    ) {}
}
