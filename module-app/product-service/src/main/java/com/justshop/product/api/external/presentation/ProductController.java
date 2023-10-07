package com.justshop.product.api.external.presentation;

import com.justshop.product.api.external.application.ProductService;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProductInfo(productId);
        return ApiResponse.ok(response);
    }

}
