package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import lombok.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Review;

import java.time.LocalDateTime;

public record ReviewDTO(
        Long id,
        String customerName,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
    public static ReviewDTO fromEntity(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getCustomerName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
