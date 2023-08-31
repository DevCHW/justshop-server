package com.justshop.domain.orderproduct.domain;

import com.justshop.domain.BaseEntity;
import com.justshop.domain.order.domain.Order;
import com.justshop.domain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProduct extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderProduct(Product product, Order order) {
        this.product = product;
        this.order = order;
    }

}
