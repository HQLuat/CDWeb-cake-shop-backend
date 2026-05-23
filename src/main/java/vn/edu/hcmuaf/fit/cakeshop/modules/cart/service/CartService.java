package vn.edu.hcmuaf.fit.cakeshop.modules.cart.service;

import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.AddItemRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.CartResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.UpdateItemRequest;

public interface CartService {

    /** Lấy giỏ hàng của user đang đăng nhập */
    CartResponse getMyCart();

    /** Thêm sản phẩm vào giỏ (nếu đã có thì cộng dồn số lượng) */
    CartResponse addItem(AddItemRequest request);

    /** Cập nhật số lượng của 1 item theo cartItemId */
    CartResponse updateItem(Long cartItemId, UpdateItemRequest request);

    /** Xóa 1 item khỏi giỏ theo cartItemId */
    CartResponse removeItem(Long cartItemId);

    /** Xóa toàn bộ giỏ hàng */
    void clearCart();
}
