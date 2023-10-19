package com.justshop.product.api.external.presentation;

import com.justshop.product.api.external.application.ProductService;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import com.justshop.response.ApiResponse;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.StringUtils.*;

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
    @Timed("products.getProductsForPage")
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProductsForPage(@RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String minPrice,
                                                                 @RequestParam(required = false) String maxPrice,
                                                                 @RequestParam(required = false) SellingStatus status,
                                                                 @RequestParam(required = false) Gender gender,
                                                                 @PageableDefault(
                                                                         page = 0, size = 10, sort = "createdDateTime", direction = Sort.Direction.ASC
                                                                 ) Pageable pageable) {

        SearchCondition searchCondition = createSearchCondition(name, minPrice, maxPrice, status, gender);
        Page<ProductResponse> response = productService.getProductsForPage(searchCondition, pageable);
        return ApiResponse.ok(response);
    }

    /* 해당 카테고리의 상품 목록 조회 (페이징) */
    @Timed("products.getProductsCategoryForPage")
    @GetMapping("/categories/{categoryId}")
    public ApiResponse<Page<ProductResponse>> getProductsCategoryForPage(@PathVariable Long categoryId,
                                                                 @RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String minPrice,
                                                                 @RequestParam(required = false) String maxPrice,
                                                                 @RequestParam(required = false) SellingStatus status,
                                                                 @RequestParam(required = false) Gender gender,
                                                                 @PageableDefault(
                                                                         page = 0, size = 10, sort = "createdDateTime", direction = Sort.Direction.ASC
                                                                 ) Pageable pageable) {
        SearchCondition searchCondition = createSearchCondition(name, minPrice, maxPrice, status, gender);
        Page<ProductResponse> response = productService.getProductsCategoryForPage(categoryId, searchCondition, pageable);
        return ApiResponse.ok(response);
    }

    private SearchCondition createSearchCondition(String name, String minPrice, String maxPrice, SellingStatus status, Gender gender) {
        return SearchCondition.builder()
                .name(name)
                .minPrice(hasText(minPrice) ? Integer.parseInt(minPrice) : null)
                .maxPrice(hasText(maxPrice) ? Integer.parseInt(maxPrice) : null)
                .status(status)
                .gender(gender)
                .build();
    }

}
