package com.justshop.order.api.external.presentation.dto.mapper;

import com.justshop.order.api.external.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.api.external.presentation.dto.request.CreateOrderRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceLayerDtoMapper {

    public static CreateOrderServiceRequest mapping(CreateOrderRequest request) {
        // 주문 상품 정보 리스트 Controller Layer -> Service Layer로 변환
        List<CreateOrderServiceRequest.OrderProductRequest> convertedOrderProducts = request.getOrderProducts().stream()
                .map(o -> CreateOrderServiceRequest.OrderProductRequest.builder()
                        .productId(o.getProductId())
                        .productOptionId(o.getProductOptionId())
                        .quantity(o.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CreateOrderServiceRequest.builder()
                .memberId(request.getMemberId())
                .orderProducts(convertedOrderProducts)
                .usePoint(request.getUsePoint())
                .couponId(request.getCouponId())
                .build();
    }

}
