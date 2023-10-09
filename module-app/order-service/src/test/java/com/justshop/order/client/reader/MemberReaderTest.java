package com.justshop.order.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.client.MemberServiceClient;
import com.justshop.order.client.response.MemberResponse;
import com.justshop.response.ApiResponse;
import feign.FeignException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberReaderTest {

    @Mock
    private MemberServiceClient memberServiceClient;

    @InjectMocks
    private MemberReader memberReader;

    @DisplayName("회원 ID를 받아 회원 서비스에서 회원 정보를 조회한다.")
    @Test
    void read_success() {
        // given
        MemberResponse response = MemberResponse.builder()
                .memberId(1L)
                .email("test@gmail.com")
                .name("testName")
                .nickname("testNickname")
                .point(1)
                .birthday(LocalDate.of(1997, 1, 3))
                .memberRole("USER")
                .gender("MAN")
                .status("ACTIVE")
                .build();

        given(memberServiceClient.getMemberInfo(anyLong()))
                .willReturn(ApiResponse.ok(response));

        // when
        MemberResponse result = memberReader.read(1L);

        // then
        assertThat(result.getMemberId()).isEqualTo(response.getMemberId());
        assertThat(result.getEmail()).isEqualTo(response.getEmail());
        assertThat(result.getName()).isEqualTo(response.getName());
        assertThat(result.getNickname()).isEqualTo(response.getNickname());
        assertThat(result.getPoint()).isEqualTo(response.getPoint());
        assertThat(result.getBirthday()).isEqualTo(response.getBirthday());
        assertThat(result.getMemberRole()).isEqualTo(response.getMemberRole());
        assertThat(result.getGender()).isEqualTo(response.getGender());
        assertThat(result.getStatus()).isEqualTo(response.getStatus());
    }

    @DisplayName("회원 정보를 조회할 때 회원 서비스에서 정보를 읽어오지 못한다면 BusinessException이 발생한다.")
    @Test
    void read_fail() {
        // given
        given(memberServiceClient.getMemberInfo(anyLong()))
                .willThrow(FeignException.class);

        // when & then
        Assertions.assertThatThrownBy(() -> memberReader.read(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("회원 시스템 장애로 인하여 주문에 실패하였습니다.");
    }

}