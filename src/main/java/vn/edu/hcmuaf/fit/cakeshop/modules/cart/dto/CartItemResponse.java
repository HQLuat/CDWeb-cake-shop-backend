package vn.edu.hcmuaf.fit.cakeshop.modules.cart.dto;

import vn.edu.hcmuaf.fit.cakeshop.modules.cart.domain.entity.CartItem;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.domain.entity.ProductImage;

import java.math.BigDecimal;
import java.util.Comparator;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subtotal,
        String imageUrl   // ảnh chính (sortOrder = 0)
) {
    public static CartItemResponse fromEntity(CartItem item) {
        String imageUrl = null;
        if (item.getProduct().getImages() != null) {
            imageUrl = item.getProduct().getImages().stream()
                    .min(Comparator.comparingInt(ProductImage::getSortOrder))
                    .map(ProductImage::getImageUrl)
                    .orElse(null);
        }

        BigDecimal unitPrice = item.getProduct().getPrice();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                unitPrice,
                item.getQuantity(),
                subtotal,
                imageUrl
        );
    }
}
