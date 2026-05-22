package vn.edu.hcmuaf.fit.cakeshop.mapper;

import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.cakeshop.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.ReviewDTO;
import vn.edu.hcmuaf.fit.cakeshop.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.entity.ProductImage;
import vn.edu.hcmuaf.fit.cakeshop.entity.Review;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDetailDTO toDetailDTO(Product product, Double avgRating, Long totalReviews, List<Review> reviews) {
        // Parse ingredients từ comma-separated string
        List<String> ingredientList = (product.getIngredients() != null && !product.getIngredients().isBlank())
                ? Arrays.asList(product.getIngredients().split(","))
                : Collections.emptyList();

        // Sort images: ảnh chính (sortOrder=0) lên đầu
        List<String> imageUrls = product.getImages() != null
                ? product.getImages().stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(this::toReviewDTO)
                .collect(Collectors.toList());

        return ProductDetailDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .detailDescription(product.getDetailDescription())
                .storageGuide(product.getStorageGuide())
                .collection(product.getCollection())
                .shippingInfo(product.getShippingInfo())
                .ingredients(ingredientList)
                .freshGuarantee(product.getFreshGuarantee())
                .averageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0)
                .totalReviews(totalReviews.intValue())
                .imageUrls(imageUrls)
                .reviews(reviewDTOs)
                .build();
    }

    public ReviewDTO toReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .customerName(review.getCustomerName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}