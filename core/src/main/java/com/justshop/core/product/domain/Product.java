package com.justshop.core.product.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.common.ErrorCode;
import com.justshop.core.product.domain.enums.ProductGender;
import com.justshop.core.product.domain.enums.ProductSellingStatus;
import com.justshop.core.product.exception.StatusNotSellingException;
import com.justshop.common.NumberUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 상품명

    private String code; // 상품 코드

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus; // 판매 상태

    private int price; // 가격

    private Long salesQuantity; // 판매 수량

    private String brandName; // 브랜드명

    @Enumerated(EnumType.STRING)
    private ProductGender gender; // 남/녀/전체 구분

    private String description; // 상품 설명

    @Builder
    public Product(String name, ProductSellingStatus sellingStatus, int price, String brandName, ProductGender gender, String description) {
        this.name = name;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.code = generateProductCode();
        this.brandName = brandName;
        this.gender = gender;
        this.description = description;
    }

    // TODO : 재고 감소 로직 구현

    // TODO : 판매수량 1 증가 로직 구현
    
    // TODO : 상품번호 생성 로직 구현

    public int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }

    // 유니크한 상품 코드 생성
    // TODO : 테스트 작성
    public String generateProductCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter) + "-" + NumberUtil.createRandomNumber(10);
    }

    public void checkSellingStatus() {
        if (! sellingStatus.equals(ProductSellingStatus.SELLING)) {
            throw new StatusNotSellingException(ErrorCode.STATUS_NOT_SELLING, "현재 판매중인 상품이 아닙니다.");
        }
    }

}
