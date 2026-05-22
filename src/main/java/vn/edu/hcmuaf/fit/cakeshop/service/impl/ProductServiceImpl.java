package vn.edu.hcmuaf.fit.cakeshop.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.dto.ReviewDTO;
import vn.edu.hcmuaf.fit.cakeshop.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.entity.Review;
import vn.edu.hcmuaf.fit.cakeshop.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.cakeshop.repository.ProductRepository;
import vn.edu.hcmuaf.fit.cakeshop.repository.ReviewRepository;
import vn.edu.hcmuaf.fit.cakeshop.service.ProductService;
import vn.edu.hcmuaf.fit.cakeshop.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetail(Long productId) {
        // Lấy product + images
        Product product = productRepository.findByIdWithImages(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + productId));

        // Lấy thống kê đánh giá
        Double avgRating = reviewRepository.findAverageRatingByProductId(productId);
        Long totalReviews = reviewRepository.countByProductId(productId);

        // Lấy 5 review mới nhất để hiển thị trong trang chi tiết
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        List<Review> recentReviews = reviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .getContent();

        return productMapper.toDetailDTO(product, avgRating, totalReviews, recentReviews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getProductReviews(Long productId, int page, int size) {
        // Kiểm tra product tồn tại
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + productId);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .map(productMapper::toReviewDTO);
    }

    @Override
    @Transactional
    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với id: " + productId));

        Review review = Review.builder()
                .product(product)
                .customerName(reviewDTO.getCustomerName())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        return productMapper.toReviewDTO(saved);
    }

    // ServiceImpl
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailDTO> getAllProducts(String search, String collection, double minPrice, double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllWithFilters(search, collection, minPrice, maxPrice, pageable);

        return products.map(product -> {
            Double avgRating = reviewRepository.findAverageRatingByProductId(product.getId());
            Long totalReviews = reviewRepository.countByProductId(product.getId());
            return productMapper.toDetailDTO(product, avgRating, totalReviews, List.of());
        });
    }
}
