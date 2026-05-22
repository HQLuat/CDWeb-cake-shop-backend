package vn.edu.hcmuaf.fit.cakeshop.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewDTO {

    private Long id;
    private String customerName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
