package vn.edu.hcmuaf.fit.cakeshop.modules.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.ProductImage;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.ProductRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.PromotionRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.AdminProductRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.AdminProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public ProductDetailDTO createProduct(AdminProductRequest req) {
        Product product = Product.builder()
                .name(req.name())
                .price(BigDecimal.valueOf(req.price()))
                .currentPrice(BigDecimal.valueOf(req.price())) // init currentPrice = price
                .description(req.description())
                .detailDescription(req.detailDescription())
                .storageGuide(req.storageGuide())
                .collection(req.collection())
                .shippingInfo(req.shippingInfo())
                .ingredients(req.ingredients())
                .freshGuarantee(req.freshGuarantee() != null ? req.freshGuarantee() : true)
                .images(new ArrayList<>())
                .reviews(new ArrayList<>())
                .build();

        product = productRepository.save(product);

        // Save images
        if (req.imageUrls() != null) {
            final Product savedProduct = product;
            List<ProductImage> images = new ArrayList<>();
            for (int i = 0; i < req.imageUrls().size(); i++) {
                String url = req.imageUrls().get(i);
                if (url != null && !url.isBlank()) {
                    images.add(ProductImage.builder()
                            .product(savedProduct)
                            .imageUrl(url.trim())
                            .sortOrder(i)
                            .build());
                }
            }
            product.getImages().addAll(images);
            product = productRepository.save(product);
        }

        return ProductDetailDTO.fromEntity(product, 0.0, 0L, List.of());
    }

    @Override
    @Transactional
    public ProductDetailDTO updateProduct(Long id, AdminProductRequest req) {
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + id));

        BigDecimal newPrice = BigDecimal.valueOf(req.price());

        product.setName(req.name());
        product.setPrice(newPrice);
        product.setDescription(req.description());
        product.setDetailDescription(req.detailDescription());
        product.setStorageGuide(req.storageGuide());
        product.setCollection(req.collection());
        product.setShippingInfo(req.shippingInfo());
        product.setIngredients(req.ingredients());
        product.setFreshGuarantee(req.freshGuarantee() != null ? req.freshGuarantee() : true);

        // Nếu không có promo đang active → cập nhật currentPrice theo price mới
        boolean hasPromo = promotionRepository.findByProductId(id).isPresent();
        if (!hasPromo) {
            product.setCurrentPrice(newPrice);
        }

        // Cập nhật images: xóa cũ, thêm mới
        product.getImages().clear();
        if (req.imageUrls() != null) {
            for (int i = 0; i < req.imageUrls().size(); i++) {
                String url = req.imageUrls().get(i);
                if (url != null && !url.isBlank()) {
                    product.getImages().add(ProductImage.builder()
                            .product(product)
                            .imageUrl(url.trim())
                            .sortOrder(i)
                            .build());
                }
            }
        }

        product = productRepository.save(product);
        return ProductDetailDTO.fromEntity(product, null, 0L, List.of());
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm: " + id);
        }
        // Xóa promotion liên quan trước (nếu có)
        promotionRepository.findByProductId(id)
                .ifPresent(p -> promotionRepository.deleteByProductId(id));

        productRepository.deleteById(id);
    }
}