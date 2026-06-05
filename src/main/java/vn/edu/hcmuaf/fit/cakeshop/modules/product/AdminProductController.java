package vn.edu.hcmuaf.fit.cakeshop.modules.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.AdminProductRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.ProductDetailDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.AdminProductService;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    // POST /api/admin/products — tạo mới
    @PostMapping
    public ResponseEntity<ProductDetailDTO> create(@RequestBody AdminProductRequest request) {
        return ResponseEntity.ok(adminProductService.createProduct(request));
    }

    // PUT /api/admin/products/{id} — cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> update(
            @PathVariable Long id,
            @RequestBody AdminProductRequest request) {
        return ResponseEntity.ok(adminProductService.updateProduct(id, request));
    }

    // DELETE /api/admin/products/{id} — xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}