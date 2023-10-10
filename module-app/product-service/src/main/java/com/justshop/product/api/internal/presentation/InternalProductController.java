package com.justshop.product.api.internal.presentation;

import com.justshop.product.api.internal.application.InternalProductService;
import com.justshop.product.api.internal.application.dto.response.OrderProductInfo;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/products")
public class InternalProductController {

    private final InternalProductService internalProductService;

    /* 주문 상품 정보 조회 */
    @GetMapping("/order-products")
    public ApiResponse<List<OrderProductInfo>> getOrderProductsInfo(@RequestParam MultiValueMap<String, String> productOptionIdMap) {
        List<Long> productOptionIds = productOptionIdMap.get("productOptionId").stream()
                .map(s -> Long.parseLong(s)).collect(Collectors.toList());

        List<OrderProductInfo> response = internalProductService.getOrderProductsInfo(productOptionIds);
        return ApiResponse.ok(response);
    }

}
