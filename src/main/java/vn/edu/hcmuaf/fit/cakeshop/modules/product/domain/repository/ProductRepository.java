package vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Fetch product kèm images theo id — dùng trong CartService
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        WHERE p.id = :id
    """)
    Optional<Product> findByIdWithImages(@Param("id") Long id);

    // Tìm kiếm có filter — dùng trong ProductService
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        WHERE (:search = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:collection = '' OR p.collection = :collection)
        AND p.price >= :minPrice
        AND p.price <= :maxPrice
    """)
    Page<Product> findAllWithFilters(
            @Param("search") String search,
            @Param("collection") String collection,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    // Query 1: load tất cả products kèm images (dùng cho PromotionService - bước 1)
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images")
    List<Product> findAllWithImages();

    // Query 2: load tất cả products kèm reviews (dùng cho PromotionService - bước 2)
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.reviews")
    List<Product> findAllWithReviews();
}