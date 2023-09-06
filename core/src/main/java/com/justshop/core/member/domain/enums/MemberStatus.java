package com.justshop.core.member.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {

    ACTIVE("활동중"), SECESSION("탈퇴");

    private final String text;

}
