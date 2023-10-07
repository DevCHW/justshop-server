package com.justshop.member.api.external.presentation.dto.mapper;

import com.justshop.member.api.external.application.dto.request.SignUpServiceRequest;
import com.justshop.member.api.external.presentation.dto.request.SignUpRequest;

public class ServiceLayerDtoMapper {

    public static SignUpServiceRequest mapping(SignUpRequest target) {
        return SignUpServiceRequest.builder()
                .email(target.getEmail())
                .password(target.getPassword())
                .name(target.getName())
                .nickname(target.getNickname())
                .birthday(target.getBirthday())
                .gender(target.getGender())
                .build();
    }

}
