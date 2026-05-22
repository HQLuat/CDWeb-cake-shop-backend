package vn.edu.hcmuaf.fit.cakeshop.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDetailDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String detailDescription;
    private String storageGuide;
    private String collection;
    private String shippingInfo;
    private List<String> ingredients;
    private Boolean freshGuarantee;

    // Rating tổng hợp (tính từ reviews)
    private Double averageRating;
    private Integer totalReviews;

    // Danh sách ảnh
    private List<String> imageUrls; // index 0 = ảnh chính

    // Danh sách đánh giá
    private List<ReviewDTO> reviews;
}
