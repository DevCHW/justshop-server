package com.justshop.apiserver.api.user.order.controller.dto.request;

import com.justshop.core.payment.domain.enums.PaymentGroup;
import com.justshop.core.payment.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {

    private Long memberId;

    private List<ProductOptionRequest> productOptions = new ArrayList<>();

    private int totalPrice;

    private int discountAmount;

    private int usePoint;

    private PaymentGroup group;

    private PaymentType type;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionRequest {
        private Long optionId;
        private Long productId;
    }

}
