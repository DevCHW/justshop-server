package com.justshop.order.domain.repository;

import com.justshop.jpa.config.QueryDslConfig;
import com.justshop.order.RepositoryTestSupport;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.OrderProduct;
import com.justshop.order.domain.entity.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@Import(QueryDslConfig.class)
class OrderRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 ID를 받아 OrderProduct와 함께 조회한다.")
    @Test
    void findWithOrderProductsById() {
        // given
        Order order = generateOrder();

        OrderProduct orderProduct1 = OrderProduct.builder()
                .productId(1L)
                .productOptionId(1L)
                .quantity(100L)
                .build();
        OrderProduct orderProduct2 = OrderProduct.builder()
                .productId(2L)
                .productOptionId(2L)
                .quantity(200L)
                .build();
        OrderProduct orderProduct3 = OrderProduct.builder()
                .productId(3L)
                .productOptionId(3L)
                .quantity(300L)
                .build();
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);
        order.addOrderProduct(orderProduct3);

        Order savedOrder = orderRepository.save(order);

        // when
        Order result = orderRepository.findWithOrderProductsById(savedOrder.getId()).get();

        // then
        assertThat(result.getOrderProducts()).hasSize(3)
                .extracting("productId", "productOptionId", "quantity")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, 100L),
                        tuple(2L, 2L, 200L),
                        tuple(3L, 3L, 300L)
                );
    }

    private Order generateOrder() {
        return Order.builder()
                .memberId(1L)
                .orderPrice(1000000L)
                .discountAmount(10000L)
                .payAmount((1000000L - 10000L))
                .status(OrderStatus.ORDERED)
                .build();
    }
}