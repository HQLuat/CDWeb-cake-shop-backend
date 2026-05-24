package vn.edu.hcmuaf.fit.cakeshop.modules.product.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ReviewDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Review;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.ProductRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.ReviewRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.ProductService;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetail(Long productId) {
        // Lấy product + images
        Product product = productRepository.findByIdWithImages(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + productId));

        // Lấy thống kê đánh giá
        Double avgRating = reviewRepository.findAverageRatingByProductId(productId);
        Long totalReviews = reviewRepository.countByProductId(productId);

        // Lấy 5 review mới nhất để hiển thị trong trang chi tiết
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        List<Review> recentReviews = reviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .getContent();

        return ProductDetailDTO.fromEntity(product, avgRating, totalReviews, recentReviews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getProductReviews(Long productId, int page, int size) {
        // Kiểm tra product tồn tại
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Không tìm thấy sản phẩm với id: " + productId);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .map(ReviewDTO::fromEntity);
    }

    @Override
    @Transactional
    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + productId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));

        Review review = Review.builder()
                .product(product)
                .customerName(user.getFullName())
                .rating(reviewDTO.rating())
                .comment(reviewDTO.comment())
                .build();

        Review saved = reviewRepository.save(review);
        return ReviewDTO.fromEntity(saved);
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
            return ProductDetailDTO.fromEntity(product, avgRating, totalReviews, List.of());
        });
    }
}
