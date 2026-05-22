package vn.edu.hcmuaf.fit.cakeshop.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.cakeshop.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.ReviewDTO;

public interface ProductService {
    ProductDetailDTO getProductDetail(Long productId);
    Page<ReviewDTO> getProductReviews(Long productId, int page, int size);
    ReviewDTO addReview(Long productId, ReviewDTO reviewDTO);
    // Service interface
    Page<ProductDetailDTO> getAllProducts(String search, String collection, double minPrice, double maxPrice, int page, int size);
}