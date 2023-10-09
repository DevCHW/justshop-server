package com.justshop.member.api.external.presentation.dto.mapper;

import com.justshop.member.api.external.application.dto.request.CreateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.request.SignUpServiceRequest;
import com.justshop.member.api.external.application.dto.request.UpdateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.presentation.dto.request.CreateDeliveryAddressRequest;
import com.justshop.member.api.external.presentation.dto.request.SignUpRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdateDeliveryAddressRequest;

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

    public static CreateDeliveryAddressServiceRequest mapping(Long memberId, CreateDeliveryAddressRequest target) {
        return CreateDeliveryAddressServiceRequest.builder()
                .memberId(memberId)
                .city(target.getCity())
                .street(target.getStreet())
                .zipcode(target.getZipcode())
                .build();
    }

    public static UpdateDeliveryAddressServiceRequest mapping(Long memberId, UpdateDeliveryAddressRequest target, Long deliveryAddressId) {
        return UpdateDeliveryAddressServiceRequest.builder()
                .memberId(memberId)
                .deliveryAddressId(deliveryAddressId)
                .city(target.getCity())
                .street(target.getStreet())
                .zipcode(target.getZipcode())
                .build();
    }

}
