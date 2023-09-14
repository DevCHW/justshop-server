package com.justshop.apiserver.user.order.application.dto;

import com.justshop.apiserver.user.order.presentation.dto.request.CreateOrderRequest;
import com.justshop.apiserver.user.order.presentation.dto.request.CreateOrderRequest.ProductOptionRequest;
import com.justshop.core.order.domain.Order;
import com.justshop.core.order.domain.enums.OrderStatus;
import com.justshop.core.payment.domain.enums.PaymentGroup;
import com.justshop.core.payment.domain.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateOrderServiceRequest {

    private Long memberId;

    @Builder.Default
    private List<ProductOptionRequest> productOptions = new ArrayList<>();

    private int totalPrice;

    private int discountAmount;

    private int usePoint;

    private PaymentGroup group;

    private PaymentType type;

    @Builder
    public CreateOrderServiceRequest(Long memberId, List<ProductOptionRequest> productOptions, int totalPrice, int discountAmount, int usePoint, PaymentGroup group, PaymentType type) {
        this.memberId = memberId;
        this.productOptions = productOptions;
        this.totalPrice = totalPrice;
        this.discountAmount = discountAmount;
        this.usePoint = usePoint;
        this.group = group;
        this.type = type;
    }

    public static CreateOrderServiceRequest from(CreateOrderRequest request) {
        return CreateOrderServiceRequest.builder()
                .memberId(request.getMemberId())
                .productOptions(request.getProductOptions())
                .totalPrice(request.getTotalPrice())
                .usePoint(request.getUsePoint())
                .group(request.getGroup())
                .type(request.getType())
                .build();
    }

    // Dto -> Entity
    public Order toEntity() {
        return Order.builder()
                .memberId(memberId)
                .orderStatus(OrderStatus.ORDER)
                .totalPrice(totalPrice)
                .discountAmount(discountAmount)
                .build();
    }

}
