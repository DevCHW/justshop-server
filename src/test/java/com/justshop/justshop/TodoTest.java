package com.justshop.justshop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TodoTest {

    @DisplayName("github action CI 정상동작 확인")
    @Test
    void todoTest() {
        // given
        int a = 1;
        int b = 1;

        // when
        int result = a + b;

        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

}
