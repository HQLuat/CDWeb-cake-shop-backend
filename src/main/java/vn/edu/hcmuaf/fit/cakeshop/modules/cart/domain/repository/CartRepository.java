package vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Chỉ fetch cart + items + product, KHÔNG fetch p.images ở đây
    // để tránh MultipleBagFetchException (2 List JOIN FETCH cùng lúc)
    // images sẽ được load tự động qua @BatchSize trên Product.images
    @Query("""
        SELECT DISTINCT c FROM Cart c
        LEFT JOIN FETCH c.items i
        LEFT JOIN FETCH i.product p
        WHERE c.user.id = :userId
    """)
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);

    boolean existsByUserId(Long userId);
}
