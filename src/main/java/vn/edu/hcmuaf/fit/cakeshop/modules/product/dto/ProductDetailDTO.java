package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import lombok.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.ProductImage;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Review;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ProductDetailDTO (
        Long id,
        String name,
        BigDecimal price,
        BigDecimal currentPrice,
        Integer discountPercent,
        String description,
        String detailDescription,
        String storageGuide,
        String collection,
        String shippingInfo,
        List<String> ingredients,
        Boolean freshGuarantee,
        Double averageRating,
        Integer totalReviews,
        List<String> imageUrls,
        List<ReviewDTO> reviews
) {
    public static ProductDetailDTO fromEntity(Product product, Double avgRating, Long totalReviews, List<Review> reviews) {

        List<String> ingredientList = (product.getIngredients() != null && !product.getIngredients().isBlank())
                ? Arrays.asList(product.getIngredients().split(","))
                : Collections.emptyList();

        List<String> imageUrls = product.getImages() != null
                ? product.getImages().stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());

        // Lấy thông tin khuyến mãi từ product entity
        BigDecimal currentPrice = product.getCurrentPrice() != null
                ? product.getCurrentPrice()
                : product.getPrice();

        Integer discountPercent = null;
        if (product.getCurrentPrice() != null
                && product.getCurrentPrice().compareTo(product.getPrice()) < 0) {
            // Tính lại % nếu entity không lưu trực tiếp
            // Hoặc lấy từ promotion nếu có
            discountPercent = product.getPrice().subtract(product.getCurrentPrice())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(product.getPrice(), 0, java.math.RoundingMode.HALF_UP)
                    .intValue();
        }

        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                currentPrice,
                discountPercent,
                product.getDescription(),
                product.getDetailDescription(),
                product.getStorageGuide(),
                product.getCollection(),
                product.getShippingInfo(),
                ingredientList,
                product.getFreshGuarantee(),
                avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0,
                totalReviews != null ? totalReviews.intValue() : 0,
                imageUrls,
                reviewDTOs
        );
    }
}
