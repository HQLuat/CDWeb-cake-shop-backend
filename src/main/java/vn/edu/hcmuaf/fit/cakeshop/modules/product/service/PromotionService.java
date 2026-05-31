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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    // --- Admin: tất cả sản phẩm kèm discount info ---
    @Transactional(readOnly = true)
    public List<PromotionDTO.PromotionProductResponse> getAllWithPromoInfo() {
        List<Product> productsWithImages = productRepository.findAllWithImages();
        List<Product> productsWithReviews = productRepository.findAllWithReviews();

        Map<Long, Product> reviewMap = productsWithReviews.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        Map<Long, Promotion> promoMap = promotionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(p -> p.getProduct().getId(), p -> p));

        return productsWithImages.stream()
                .map(p -> toResponse(p, reviewMap.get(p.getId()), promoMap.get(p.getId())))
                .collect(Collectors.toList());
    }

    // --- Public: CHỈ sản phẩm đang có khuyến mãi ---
    // @Transactional giữ session mở → lazy load product/images/reviews hoạt động bình thường
    @Transactional(readOnly = true)
    public List<PromotionDTO.PromotionProductResponse> getActivePromotions() {
        List<Promotion> promos = promotionRepository.findAllPromotions();

        return promos.stream().map(promo -> {
            Product p = promo.getProduct(); // lazy load — OK vì còn trong transaction

            // Trigger load images & reviews trong session
            List<String> imageUrls = p.getImages() == null ? List.of()
                    : p.getImages().stream()
                    .sorted(Comparator.comparingInt(img ->
                            img.getSortOrder() != null ? img.getSortOrder() : 0))
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
                    promo.getDiscountPercent(),
                    promo.getDiscountedPrice()
            );
        }).collect(Collectors.toList());
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

        // Build response trực tiếp không qua lazy load
        List<String> imageUrls = product.getImages() == null ? List.of()
                : product.getImages().stream()
                .sorted(Comparator.comparingInt(img ->
                        img.getSortOrder() != null ? img.getSortOrder() : 0))
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList());

        double avgRating = product.getReviews() == null || product.getReviews().isEmpty() ? 0.0
                : product.getReviews().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);

        return new PromotionDTO.PromotionProductResponse(
                product.getId(), product.getName(), product.getDescription(),
                product.getCollection(), product.getPrice(),
                product.getCurrentPrice(), avgRating,
                product.getReviews() == null ? 0 : product.getReviews().size(),
                imageUrls, promo.getDiscountPercent(), promo.getDiscountedPrice()
        );
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

    private PromotionDTO.PromotionProductResponse toResponse(
            Product productWithImages,
            Product productWithReviews,
            Promotion promo) {

        List<String> imageUrls = productWithImages.getImages() == null ? List.of()
                : productWithImages.getImages().stream()
                .sorted(Comparator.comparingInt(img ->
                        img.getSortOrder() != null ? img.getSortOrder() : 0))
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList());

        double avgRating = productWithReviews == null
                || productWithReviews.getReviews() == null
                || productWithReviews.getReviews().isEmpty() ? 0.0
                : productWithReviews.getReviews().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);

        int totalReviews = productWithReviews == null
                || productWithReviews.getReviews() == null ? 0
                : productWithReviews.getReviews().size();

        Product p = productWithImages;
        return new PromotionDTO.PromotionProductResponse(
                p.getId(), p.getName(), p.getDescription(), p.getCollection(),
                p.getPrice(),
                p.getCurrentPrice() != null ? p.getCurrentPrice() : p.getPrice(),
                avgRating, totalReviews, imageUrls,
                promo != null ? promo.getDiscountPercent() : null,
                promo != null ? promo.getDiscountedPrice() : null
        );
    }
}