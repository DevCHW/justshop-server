package com.justshop.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("회원"), ADMIN("관리자"), GUEST("손님");

    private final String description;
}
