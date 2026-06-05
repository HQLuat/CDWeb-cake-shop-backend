package vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Giá hiệu lực — bằng price khi không có promo, bằng discountedPrice khi đang giảm giá
    // Tất cả API public đọc field này thay vì price
    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String detailDescription;

    @Column(columnDefinition = "TEXT")
    private String storageGuide;

    private String collection;

    private String shippingInfo;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    private Boolean freshGuarantee = true;

    @BatchSize(size = 30)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @BatchSize(size = 30)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}