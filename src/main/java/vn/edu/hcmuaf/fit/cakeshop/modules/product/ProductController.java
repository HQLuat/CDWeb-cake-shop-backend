package vn.edu.hcmuaf.fit.cakeshop.modules.product;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ReviewDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET /api/products/{id}
     * Lấy toàn bộ thông tin chi tiết sản phẩm (dùng cho trang ProductDetail)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetail(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    /**
     * GET /api/products/{id}/reviews?page=0&size=10
     * Phân trang danh sách đánh giá
     */
    @GetMapping("/{id}/reviews")
    public ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getProductReviews(id, page, size));
    }

    /**
     * POST /api/products/{id}/reviews
     * Thêm đánh giá mới
     */
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewDTO
    ) {
        return ResponseEntity.ok(productService.addReview(id, reviewDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDetailDTO>> getAllProducts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String collection,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "999999") double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(search, collection, minPrice, maxPrice, page, size));
    }
}
