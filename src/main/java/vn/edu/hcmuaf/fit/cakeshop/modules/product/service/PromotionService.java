package vn.edu.hcmuaf.fit.cakeshop.modules.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Promotion;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.PromotionDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.ProductRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.PromotionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    // --- Lấy tất cả sản phẩm kèm thông tin discount ---
    // Tách 2 query riêng để tránh MultipleBagFetchException
    @Transactional(readOnly = true)
    public List<PromotionDTO.PromotionProductResponse> getAllWithPromoInfo() {
        // Query 1: load products + images
        List<Product> products = productRepository.findAllWithImages();

        // Query 2: load reviews vào đúng các product đã có (Hibernate merge vào 1st-level cache)
        productRepository.findAllWithReviews();

        // Query 3: load promotions
        Map<Long, Promotion> promoMap = promotionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(p -> p.getProduct().getId(), p -> p));

        return products.stream()
                .map(p -> toResponse(p, promoMap.get(p.getId())))
                .collect(Collectors.toList());
    }

    // --- Áp dụng / cập nhật giảm giá 1 sản phẩm ---
    @Transactional
    public PromotionDTO.PromotionProductResponse applyPromotion(PromotionDTO.ApplyPromotionRequest req) {
        Product product = findProduct(req.productId());

        BigDecimal discounted = calcDiscounted(product.getPrice(), req.discountPercent());

        Promotion promo = promotionRepository.findByProductId(req.productId())
                .orElse(Promotion.builder().product(product).build());
        promo.setDiscountPercent(req.discountPercent());
        promo.setDiscountedPrice(discounted);
        promotionRepository.save(promo);

        product.setCurrentPrice(discounted);
        productRepository.save(product);

        return toResponse(product, promo);
    }

    // --- Áp dụng hàng loạt ---
    @Transactional
    public void bulkApplyPromotion(PromotionDTO.BulkApplyPromotionRequest req) {
        for (Long productId : req.productIds()) {
            applyPromotion(new PromotionDTO.ApplyPromotionRequest(productId, req.discountPercent()));
        }
    }

    // --- Xóa khuyến mãi, khôi phục giá gốc ---
    @Transactional
    public void removePromotion(Long productId) {
        Product product = findProduct(productId);
        promotionRepository.deleteByProductId(productId);

        product.setCurrentPrice(product.getPrice());
        productRepository.save(product);
    }

    // ---- Helpers ----

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    private BigDecimal calcDiscounted(BigDecimal price, int discountPercent) {
        return price
                .multiply(BigDecimal.valueOf(100 - discountPercent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private PromotionDTO.PromotionProductResponse toResponse(Product p, Promotion promo) {
        List<String> imageUrls = p.getImages() == null ? List.of()
                : p.getImages().stream()
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList());

        double avgRating = p.getReviews() == null || p.getReviews().isEmpty() ? 0.0
                : p.getReviews().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);
        int totalReviews = p.getReviews() == null ? 0 : p.getReviews().size();

        return new PromotionDTO.PromotionProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getCollection(),
                p.getPrice(),
                p.getCurrentPrice() != null ? p.getCurrentPrice() : p.getPrice(),
                avgRating,
                totalReviews,
                imageUrls,
                promo != null ? promo.getDiscountPercent() : null,
                promo != null ? promo.getDiscountedPrice() : null
        );
    }
}