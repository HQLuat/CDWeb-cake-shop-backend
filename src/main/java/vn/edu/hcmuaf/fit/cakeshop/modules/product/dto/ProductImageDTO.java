package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.ProductImage;

/**
 * DTO chứa đầy đủ thông tin ảnh sản phẩm, bao gồm cả publicId để admin có thể
 * xóa/thay thế ảnh trên Cloudinary khi cập nhật sản phẩm.
 */
public record ProductImageDTO(
        String imageUrl,
        String cloudinaryPublicId,
        Integer sortOrder
) {
    public static ProductImageDTO fromEntity(ProductImage img) {
        return new ProductImageDTO(img.getImageUrl(), img.getCloudinaryPublicId(), img.getSortOrder());
    }
}
