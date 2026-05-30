package vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Promotion;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}

