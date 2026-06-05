package vn.edu.hcmuaf.fit.cakeshop.modules.product.service;

import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.AdminProductRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;

public interface AdminProductService {
    ProductDetailDTO createProduct(AdminProductRequest request);
    ProductDetailDTO updateProduct(Long id, AdminProductRequest request);
    void deleteProduct(Long id);
}
