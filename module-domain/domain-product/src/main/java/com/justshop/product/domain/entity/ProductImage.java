package com.justshop.product.domain.entity;

import com.justshop.jpa.converter.BooleanToYNConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 상품 정보

    private Long fileId; // 파일 ID

    @Convert(converter = BooleanToYNConverter.class)
    private boolean basicYn; // 기본이미지 여부

    @Builder
    public ProductImage(Product product, Long fileId, boolean basicYn) {
        this.product = product;
        this.fileId = fileId;
        this.basicYn = basicYn;
    }
}
