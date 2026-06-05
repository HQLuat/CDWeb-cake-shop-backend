package vn.edu.hcmuaf.fit.cakeshop.modules.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage.CloudinaryService;
import vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage.CloudinaryUploadResponse;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public CloudinaryUploadResponse uploadProductImage(MultipartFile file) {
        return cloudinaryService.uploadImage(file, "cakeshop/products");
    }

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

        // Save images kèm publicId
        if (req.images() != null) {
            final Product savedProduct = product;
            List<ProductImage> images = new ArrayList<>();
            for (int i = 0; i < req.images().size(); i++) {
                AdminProductRequest.ImageUpload img = req.images().get(i);
                if (img != null && img.url() != null && !img.url().isBlank()) {
                    images.add(ProductImage.builder()
                            .product(savedProduct)
                            .imageUrl(img.url().trim())
                            .cloudinaryPublicId(img.publicId())
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

        // Xác định publicId của ảnh cũ nào bị xóa → cần destroy trên Cloudinary
        Set<String> newPublicIds = (req.images() != null)
                ? req.images().stream()
                        .filter(img -> img.publicId() != null && !img.publicId().isBlank())
                        .map(AdminProductRequest.ImageUpload::publicId)
                        .collect(Collectors.toSet())
                : Set.of();

        // Xóa trên Cloudinary các ảnh cũ không còn trong danh sách mới
        if (product.getImages() != null) {
            for (ProductImage oldImage : product.getImages()) {
                String pid = oldImage.getCloudinaryPublicId();
                if (pid != null && !newPublicIds.contains(pid)) {
                    cloudinaryService.deleteImage(pid);
                }
            }
        }

        // Xóa toàn bộ ảnh cũ trong DB (orphanRemoval sẽ xóa DB records)
        product.getImages().clear();

        // Thêm ảnh mới theo thứ tự mới
        if (req.images() != null) {
            for (int i = 0; i < req.images().size(); i++) {
                AdminProductRequest.ImageUpload img = req.images().get(i);
                if (img != null && img.url() != null && !img.url().isBlank()) {
                    product.getImages().add(ProductImage.builder()
                            .product(product)
                            .imageUrl(img.url().trim())
                            .cloudinaryPublicId(img.publicId())
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
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + id));

        // Xóa ảnh trên Cloudinary trước khi xóa DB
        if (product.getImages() != null) {
            product.getImages().forEach(image -> {
                if (image.getCloudinaryPublicId() != null) {
                    cloudinaryService.deleteImage(image.getCloudinaryPublicId());
                }
            });
        }

        // Xóa promotion liên quan trước (nếu có)
        promotionRepository.findByProductId(id)
                .ifPresent(p -> promotionRepository.deleteByProductId(id));

        productRepository.deleteById(id);
    }
}