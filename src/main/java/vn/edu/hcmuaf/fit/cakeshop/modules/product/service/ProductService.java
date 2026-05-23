package vn.edu.hcmuaf.fit.cakeshop.modules.product.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ReviewDTO;

public interface ProductService {
    ProductDetailDTO getProductDetail(Long productId);
    Page<ReviewDTO> getProductReviews(Long productId, int page, int size);
    ReviewDTO addReview(Long productId, ReviewDTO reviewDTO);
    Page<ProductDetailDTO> getAllProducts(String search, String collection, double minPrice, double maxPrice, int page, int size);
}