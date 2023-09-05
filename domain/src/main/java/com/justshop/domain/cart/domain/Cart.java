package com.justshop.domain.cart.domain;

import com.justshop.domain.BaseEntity;
import com.justshop.domain.member.domain.Member;
import com.justshop.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany
    private List<Product> productNumber = new ArrayList<>();

    private int totalPrice;

    // TODO : 상품이 추가될 때 마다, totalPrice 증가 로직 구현

}
