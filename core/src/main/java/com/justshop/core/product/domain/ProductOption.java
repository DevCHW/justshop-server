package com.justshop.core.product.domain;

import com.justshop.core.common.ErrorCode;
import com.justshop.core.product.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품_옵션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 상품 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option; // 옵션 ID

    private int stockQuantity; // 재고 수량

    private int salesQuantity; // 판매 수량

    // 재고 감소
    public void deductStockQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK, "재고가 부족합니다.");
        }

        this.stockQuantity -= quantity;
    }
}
