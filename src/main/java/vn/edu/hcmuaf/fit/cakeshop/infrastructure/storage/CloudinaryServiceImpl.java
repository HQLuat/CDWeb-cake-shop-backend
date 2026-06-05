package vn.edu.hcmuaf.fit.cakeshop.infrastructure.storage;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryUploadResponse uploadImage(MultipartFile file, String folder) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );
            String url = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            return new CloudinaryUploadResponse(url, publicId);
        } catch (IOException e) {
            log.error("Lỗi khi upload ảnh lên Cloudinary: {}", e.getMessage(), e);
            throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Map.of("resource_type", "image"));
            log.info("Đã xóa ảnh Cloudinary: {}", publicId);
        } catch (Exception e) {
            // Không để lỗi xóa Cloudinary chặn luồng xóa DB
            log.warn("Không thể xóa ảnh Cloudinary [{}]: {}", publicId, e.getMessage());
        }
    }
}
