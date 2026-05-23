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
        String description,
        String detailDescription,
        String storageGuide,
        String collection,
        String shippingInfo,
        List<String> ingredients,
        Boolean freshGuarantee,

        // Rating tổng hợp (tính từ reviews)
        Double averageRating,
        Integer totalReviews,

        // Danh sách ảnh
        List<String> imageUrls, // index 0 = ảnh chính

        // Danh sách đánh giá
        List<ReviewDTO> reviews
) {
    public static ProductDetailDTO fromEntity(Product product, Double avgRating, Long totalReviews, List<Review> reviews) {

        // Logic xử lý nguyên liệu (từ String -> List)
        List<String> ingredientList = (product.getIngredients() != null && !product.getIngredients().isBlank())
                ? Arrays.asList(product.getIngredients().split(","))
                : Collections.emptyList();

        // Logic xử lý và sắp xếp ảnh (sortOrder=0 lên đầu)
        List<String> imageUrls = product.getImages() != null
                ? product.getImages().stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList())
                : Collections.emptyList();

        // Chuyển đổi list review sang DTO (Giả sử ReviewDTO cũng có phương thức từ Entity)
        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());

        // Trả về instance của record
        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
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
