package vn.edu.hcmuaf.fit.cakeshop.modules.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cakeshop.modules.auth.domain.repository.AuthRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.Cart;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.CartItem;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.repository.CartItemRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.repository.CartRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.AddItemRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.CartResponse;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto.UpdateItemRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.cart.service.CartService;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.Product;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.repository.ProductRepository;
import vn.edu.hcmuaf.fit.cakeshop.modules.user.domain.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AuthRepository userRepository;

    // -------------------------------------------------------
    // Helper: lấy User đang đăng nhập từ SecurityContext
    // -------------------------------------------------------
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + username));
    }

    // -------------------------------------------------------
    // Helper: lấy hoặc tạo mới Cart cho user
    // -------------------------------------------------------
    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUserIdWithItems(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    return cartRepository.save(newCart);
                });
    }

    // -------------------------------------------------------
    // Lấy giỏ hàng
    // -------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        return CartResponse.fromEntity(cart);
    }

    // -------------------------------------------------------
    // Thêm sản phẩm
    // -------------------------------------------------------
    @Override
    @Transactional
    public CartResponse addItem(AddItemRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findByIdWithImages(request.productId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + request.productId()));

        // Kiểm tra sản phẩm đã trong giỏ chưa
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            // Cộng dồn số lượng
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            cartItemRepository.save(item);
        } else {
            // Thêm mới
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.quantity())
                    .build();
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        // Reload để lấy dữ liệu mới nhất
        Cart updatedCart = cartRepository.findByUserIdWithItems(user.getId()).orElse(cart);
        return CartResponse.fromEntity(updatedCart);
    }

    // -------------------------------------------------------
    // Cập nhật số lượng
    // -------------------------------------------------------
    @Override
    @Transactional
    public CartResponse updateItem(Long cartItemId, UpdateItemRequest request) {
        User user = getCurrentUser();

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy item trong giỏ hàng"));

        // Kiểm tra item thuộc giỏ của user đang đăng nhập
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền chỉnh sửa item này");
        }

        item.setQuantity(request.quantity());
        cartItemRepository.save(item);

        Cart updatedCart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return CartResponse.fromEntity(updatedCart);
    }

    // -------------------------------------------------------
    // Xóa 1 item
    // -------------------------------------------------------
    @Override
    @Transactional
    public CartResponse removeItem(Long cartItemId) {
        User user = getCurrentUser();

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy item trong giỏ hàng"));

        // Kiểm tra quyền sở hữu
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền xóa item này");
        }

        cartItemRepository.delete(item);

        Cart updatedCart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return CartResponse.fromEntity(updatedCart);
    }

    // -------------------------------------------------------
    // Xóa toàn bộ giỏ
    // -------------------------------------------------------
    @Override
    @Transactional
    public void clearCart() {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
