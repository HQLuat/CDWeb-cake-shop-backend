package vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_images",
        uniqueConstraints = {
                // Cùng product + cùng imageUrl thì không insert lại
                @UniqueConstraint(name = "uk_product_image_url", columnNames = {"product_id", "image_url"})
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String imageUrl;

    private Integer sortOrder = 0; // ảnh chính có sortOrder = 0
}