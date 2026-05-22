package vn.edu.hcmuaf.fit.cakeshop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String detailDescription; // nội dung tab "Mô tả chi tiết"

    @Column(columnDefinition = "TEXT")
    private String storageGuide; // nội dung tab "Hướng dẫn bảo quản"

    private String collection; // VD: "Signature Collection"

    private String shippingInfo;

    @Column(columnDefinition = "TEXT")
    private String ingredients; // lưu dạng JSON string hoặc comma-separated

    private Boolean freshGuarantee = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}