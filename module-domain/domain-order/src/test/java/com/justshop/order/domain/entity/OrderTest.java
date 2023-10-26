package com.justshop.order.domain.entity;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.domain.entity.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class OrderTest {

    @DisplayName("주문 취소를 하여 주문 상태가 취소상태로 바뀐다.")
    @Test
    void order_cancel_success() {
        // given
        Order order = Order.builder()
                .memberId(1L)
                .orderPrice(1000L)
                .discountAmount(1000L)
                .payAmount(1000L)
                .status(OrderStatus.ORDERED)
                .build();
        // when
        order.cancel();

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @DisplayName("주문 취소를 할 때 주문 상태가 처리완료상태라면 BusinessException이 발생한다..")
    @Test
    void order_cancel_fail() {
        // given
        Order order = Order.builder()
                .memberId(1L)
                .orderPrice(1000L)
                .discountAmount(1000L)
                .payAmount(1000L)
                .status(OrderStatus.COMPLETE)
                .build();
        // when & then
        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(BusinessException.class)
                .hasMessage("처리 완료된 주문건은 취소가 불가합니다.");

    }

    @DisplayName("주문의 상태를 주문 실패 상태로 바꿀 수 있다.")
    @Test
    void order_fail_success() {
        // given
        Order order = Order.builder()
                .memberId(1L)
                .orderPrice(1000L)
                .discountAmount(1000L)
                .payAmount(1000L)
                .status(OrderStatus.INIT)
                .build();

        // when
        order.fail();

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.FAIL);
    }

    @DisplayName("주문의 상태를 주문 상태로 바꿀 수 있다.")
    @Test
    void order_success_success() {
        // given
        Order order = Order.builder()
                .memberId(1L)
                .orderPrice(1000L)
                .discountAmount(1000L)
                .payAmount(1000L)
                .status(OrderStatus.INIT)
                .build();

        // when
        order.success();

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDERED);
    }
}