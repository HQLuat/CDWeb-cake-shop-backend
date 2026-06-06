package vn.edu.hcmuaf.fit.cakeshop.modules.product.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage.CloudinaryUploadResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.AdminProductRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;

public interface AdminProductService {
    ProductDetailDTO createProduct(AdminProductRequest request);
    ProductDetailDTO updateProduct(Long id, AdminProductRequest request);
    void deleteProduct(Long id);

    /**
     * Upload một ảnh lên Cloudinary và trả về URL + publicId.
     * Endpoint riêng giúp preview trước khi lưu sản phẩm.
     */
    CloudinaryUploadResponse uploadProductImage(MultipartFile file);

    /**
     * Xóa một ảnh tạm hoặc ảnh đã upload khỏi Cloudinary qua publicId.
     */
    void deleteProductImage(String publicId);
}
