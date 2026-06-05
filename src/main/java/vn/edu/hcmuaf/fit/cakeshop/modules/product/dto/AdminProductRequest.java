package vn.edu.hcmuaf.fit.cakeshop.modules.product.dto;

import java.util.List;

public record AdminProductRequest(
        String name,
        Double price,
        String description,
        String detailDescription,
        String storageGuide,
        String collection,
        String shippingInfo,
        String ingredients,     // comma-separated string
        Boolean freshGuarantee,
        List<String> imageUrls  // list URL ảnh, index 0 = ảnh chính
) {}