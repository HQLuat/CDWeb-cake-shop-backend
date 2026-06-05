package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import java.util.List;

public record AdminProductRequest(
        String name,
        Double price,
        String description,
        String detailDescription,
        String storageGuide,
        String collection,
        String shippingInfo,
        String ingredients,     // comma-separated string
        Boolean freshGuarantee,
        List<ImageUpload> images  // thay thế List<String> imageUrls — index 0 = ảnh chính
) {
    /**
     * Một entry ảnh: URL (đã upload lên Cloudinary) + publicId (để xóa khi cần).
     */
    public record ImageUpload(
            String url,
            String publicId
    ) {}
}