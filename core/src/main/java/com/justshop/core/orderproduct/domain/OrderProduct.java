package com.justshop.core.orderproduct.domain;

import com.justshop.core.product.domain.Product;
import com.justshop.core.BaseEntity;
import com.justshop.core.order.domain.Order;
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
