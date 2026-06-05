package vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage;

/**
 * Response DTO sau khi upload ảnh lên Cloudinary thành công.
 *
 * @param url      URL công khai (secure_url) của ảnh
 * @param publicId Public ID của ảnh trên Cloudinary (dùng để xóa sau này)
 */
public record CloudinaryUploadResponse(String url, String publicId) {
}
