package com.justshop.product.domain.entity;

import com.justshop.core.exception.BusinessException;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Size;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductOptionTest {

    @DisplayName("감소시킬 재고수량을 받아서 재고수량을 감소시킨다.")
    @Test
    void decreaseStock_success() {
        // given
        ProductOption productOption = ProductOption.builder()
                .productSize(Size.S)
                .color(Color.BLACK)
                .etc(null)
                .additionalPrice(0)
                .stockQuantity(1000)
                .build();

        // when
        productOption.decreaseStock(1000L);

        // then
        Assertions.assertThat(productOption.getStockQuantity()).isEqualTo(0);
    }

    @DisplayName("현재 재고가 감소시킬 재고보다 부족하면 BusinessException이 발생한다.")
    @Test
    void decreaseStock_fail() {
        // given
        ProductOption productOption = ProductOption.builder()
                .productSize(Size.S)
                .color(Color.BLACK)
                .etc(null)
                .additionalPrice(0)
                .stockQuantity(1000)
                .build();

        // when & then
        assertThatThrownBy(() -> productOption.decreaseStock(1001L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("재고가 부족합니다.");
    }

}