package com.justshop.core.kafka.message.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreate {
    private Long memberId; // 회원 ID
}
