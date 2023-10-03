package com.justshop.product.api.product.presentation;

import com.justshop.product.api.product.application.ProductService;
import com.justshop.product.api.product.application.dto.response.ProductPriceResponse;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

}
