package com.justshop.product.domain.entity;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Size;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 옵션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 상품 정보

    @Enumerated(EnumType.STRING)
    private Size productSize; // 사이즈

    @Enumerated(EnumType.STRING)
    private Color color; // 색상

    private String etc; // 기타
    private int additionalPrice; // 추가금액
    private int stockQuantity; //재고수량

    @Builder
    public ProductOption(Size productSize, Color color, String etc, int additionalPrice, int stockQuantity) {
        this.productSize = productSize;
        this.color = color;
        this.etc = etc;
        this.additionalPrice = additionalPrice;
        this.stockQuantity = stockQuantity;
    }

    // 연관관계 편의 메서드
    public void addProduct(Product product) {
        this.product = product;
    }

    // 재고수량 감소
    public void decreaseStock(Long quantity) {
        if (stockQuantity < quantity) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_STOCK, "재고가 부족합니다.");
        }

        this.stockQuantity -= quantity;
    }

}
