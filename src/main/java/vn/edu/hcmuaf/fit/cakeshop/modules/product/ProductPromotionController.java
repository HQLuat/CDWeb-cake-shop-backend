package vn.edu.hcmuaf.fit.cakeshop.modules.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.dto.PromotionDTO;
import vn.edu.hcmuaf.fit.cakeshop.modules.product.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/products/promotions")
@RequiredArgsConstructor
public class ProductPromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<PromotionDTO.PromotionProductResponse>> getPublicPromos() {
        return ResponseEntity.ok(promotionService.getActivePromotions());
    }
}