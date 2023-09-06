package com.justshop.core.member.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    MAN("남자"), WOMEN("여자");

    private final String description;

}