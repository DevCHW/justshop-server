package com.justshop.member.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DeliveryAddressTest {

    @DisplayName("basicYn 필드가 true라면 false로, false라면 true로 변경한다.")
    @Test
    void changeBasicYn() {
        // given
        DeliveryAddress basicAddress1 = DeliveryAddress.builder()
                .basicYn(true)
                .build();
        DeliveryAddress basicAddress2 = DeliveryAddress.builder()
                .basicYn(false)
                .build();

        // when
        basicAddress1.changeBasicYn();
        basicAddress2.changeBasicYn();

        // then
        assertThat(basicAddress1.isBasicYn()).isFalse();
        assertThat(basicAddress2.isBasicYn()).isTrue();
    }

}