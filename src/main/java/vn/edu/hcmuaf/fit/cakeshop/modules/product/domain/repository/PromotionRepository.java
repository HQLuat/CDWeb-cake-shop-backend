package vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByProductId(Long productId);
    void deleteByProductId(Long productId);

    // Dùng query đơn giản — @Transactional trong service giữ session
    // để lazy load product, images, reviews không bị lỗi
    @Query("SELECT pr FROM Promotion pr")
    List<Promotion> findAllPromotions();
}