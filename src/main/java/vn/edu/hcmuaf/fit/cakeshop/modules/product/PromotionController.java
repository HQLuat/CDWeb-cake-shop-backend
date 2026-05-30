package vn.edu.hcmuaf.fit.cakeshop.modules.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.PromotionDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promotions")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    // GET /admin/promotions
    @GetMapping
    public ResponseEntity<List<PromotionDTO.PromotionProductResponse>> getAll() {
        return ResponseEntity.ok(promotionService.getAllWithPromoInfo());
    }

    // PUT /admin/promotions/apply
    @PutMapping("/apply")
    public ResponseEntity<PromotionDTO.PromotionProductResponse> apply(
            @RequestBody PromotionDTO.ApplyPromotionRequest request) {
        return ResponseEntity.ok(promotionService.applyPromotion(request));
    }

    // PUT /admin/promotions/bulk-apply
    @PutMapping("/bulk-apply")
    public ResponseEntity<Void> bulkApply(
            @RequestBody PromotionDTO.BulkApplyPromotionRequest request) {
        promotionService.bulkApplyPromotion(request);
        return ResponseEntity.ok().build();
    }

    // DELETE /admin/promotions/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> remove(@PathVariable Long productId) {
        promotionService.removePromotion(productId);
        return ResponseEntity.ok().build();
    }
}