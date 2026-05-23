package vn.edu.hcmuaf.fit.cakeshop.modules.cart;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.common.ApiResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.AddItemRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.CartResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.UpdateItemRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.service.CartService;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * GET /api/cart
     * Lấy giỏ hàng của user hiện tại
     */
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getMyCart() {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy giỏ hàng thành công",
                cartService.getMyCart()
        ));
    }

    /**
     * POST /api/cart/items
     * Thêm sản phẩm vào giỏ hàng (nếu đã có thì cộng dồn số lượng)
     */
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addItem(
            @Valid @RequestBody AddItemRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Thêm sản phẩm vào giỏ hàng thành công",
                cartService.addItem(request)
        ));
    }

    /**
     * PUT /api/cart/items/{cartItemId}
     * Cập nhật số lượng của 1 item
     */
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateItemRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Cập nhật giỏ hàng thành công",
                cartService.updateItem(cartItemId, request)
        ));
    }

    /**
     * DELETE /api/cart/items/{cartItemId}
     * Xóa 1 sản phẩm khỏi giỏ hàng
     */
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> removeItem(
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Xóa sản phẩm khỏi giỏ hàng thành công",
                cartService.removeItem(cartItemId)
        ));
    }

    /**
     * DELETE /api/cart
     * Xóa toàn bộ giỏ hàng
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(ApiResponse.success("Đã xóa toàn bộ giỏ hàng"));
    }
}
