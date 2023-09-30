package com.justshop.member.presentation.dto.mapper;

import com.justshop.member.application.dto.SignUpServiceRequest;
import com.justshop.member.presentation.dto.SignUpRequest;

public class ServiceLayerDtoMapper {

    public static SignUpServiceRequest mapping(SignUpRequest target) {
        return SignUpServiceRequest.builder()
                .loginId(target.getLoginId())
                .password(target.getPassword())
                .name(target.getName())
                .nickname(target.getNickname())
                .point(target.getPoint())
                .birthday(target.getBirthday())
                .memberRole(target.getMemberRole())
                .build();
    }

}
