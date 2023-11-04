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

    @Convert(converter = BooleanToYNConverter.class)
    private boolean basicYn; // 기본이미지 여부

    private String saveFileName;
    private String originFileName;
    private String path;

    @Builder
    public ProductImage(String saveFileName, String originFileName, String path, boolean basicYn) {
        this.product = product;
        this.basicYn = basicYn;
        this.saveFileName = saveFileName;
        this.originFileName = originFileName;
        this.path = path;
    }

    public void addProduct(Product product) {
        this.product = product;
    }
}
