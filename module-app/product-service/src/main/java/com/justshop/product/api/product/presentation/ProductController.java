package com.justshop.product.api.product.presentation;

import com.justshop.product.api.product.application.ProductService;
import com.justshop.product.api.product.application.dto.response.ProductPriceResponse;
import com.justshop.product.api.product.application.dto.response.ProductResponse;
import com.justshop.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // 주문된 상품 옵션 id 들을 입력받아서 가격정보 조회
    @GetMapping("/prices")
    public ApiResponse<List<ProductPriceResponse>> getOrderProductsInfo(@RequestParam MultiValueMap<String, String> productOptionIdMap) {
        List<Long> productOptionIds = productOptionIdMap.get("productOptionId").stream()
                .map(s -> Long.parseLong(s)).collect(Collectors.toList());

        List<ProductPriceResponse> response = productService.getOrderProductsInfo(productOptionIds);
        return ApiResponse.ok(response);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProductInfo(productId);
        return ApiResponse.ok(response);
    }

}
