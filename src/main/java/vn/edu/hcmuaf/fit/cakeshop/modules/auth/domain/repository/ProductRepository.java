package vn.edu.hcmuaf.fit.cakeshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cakeshop.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Fetch product cùng images và reviews trong 1 query (tránh N+1)
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        WHERE p.id = :id
    """)
    Optional<Product> findByIdWithImages(@Param("id") Long id);

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
}