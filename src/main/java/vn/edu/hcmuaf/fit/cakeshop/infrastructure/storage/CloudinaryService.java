package vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    /**
     * Upload một file ảnh lên Cloudinary.
     *
     * @param file   MultipartFile nhận từ request
     * @param folder Tên folder trên Cloudinary (vd: "cakeshop/products")
     * @return CloudinaryUploadResponse chứa URL công khai và publicId
     */
    CloudinaryUploadResponse uploadImage(MultipartFile file, String folder);

    /**
     * Xóa ảnh khỏi Cloudinary theo publicId.
     * Lỗi không làm hỏng luồng chính — nên được gọi trong try-catch.
     *
     * @param publicId Public ID của ảnh (được lưu trong ProductImage.cloudinaryPublicId)
     */
    void deleteImage(String publicId);
}
