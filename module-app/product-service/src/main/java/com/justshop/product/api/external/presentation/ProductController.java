package com.justshop.product.api.external.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.product.api.external.application.ProductService;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /* 상품 상세 조회 */
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProductInfo(productId);
        return ApiResponse.ok(response);
    }

    /* 상품 목록 조회 (페이징) */
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProductsForPage(@RequestParam(required = false) SearchCondition searchCondition,
                                                                 @PageableDefault(
                                                                         page = 0, size = 10, sort = "createdDateTime", direction = Sort.Direction.ASC
                                                                 ) Pageable pageable) {
        if (Objects.isNull(searchCondition)) {
            searchCondition = new SearchCondition();
        }

        Page<ProductResponse> response = productService.getProductsForPage(searchCondition, pageable);
        return ApiResponse.ok(response);
    }

}
