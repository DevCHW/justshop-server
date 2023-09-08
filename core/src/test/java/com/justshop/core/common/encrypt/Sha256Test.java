package com.justshop.core.common.encrypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Sha256Test {

    @DisplayName("문자열을 SHA-256 방식으로 암호화한다.")
    @Test
    void encrypt1() {
        // given
        String target = "encrypt Test";

        // when
        String result = Sha256.encrypt(target);

        // then
        assertThat(result).isNotEqualTo(target);
    }

    @DisplayName("암호화되어있는 문자열과 비교하려면 암호화하여 비교하여야 한다.")
    @Test
    void encrypt2() {
        // given
        String target = "encrypt Test";

        // when
        String result = Sha256.encrypt(target);

        // then
        assertThat(result).isEqualTo(Sha256.encrypt(target));
    }

}