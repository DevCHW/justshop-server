package com.justshop.order.client.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductPriceResponse {

    private Long productOptionId; // 상품 옵션 ID
    private Long productId; // 상품 ID
    private Integer price; // 가격
    private int additionalPrice; // 추가금액
    private int stockQuantity; //재고수량

}
