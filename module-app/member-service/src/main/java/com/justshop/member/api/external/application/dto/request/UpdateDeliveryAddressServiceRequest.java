package com.justshop.member.api.external.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateDeliveryAddressServiceRequest {

    private Long memberId; // 회원 ID
    private Long deliveryAddressId;
    private String city; // 주소 1
    private String street; // 주소 2
    private String zipcode; // 주소 3

    @Builder
    public UpdateDeliveryAddressServiceRequest(Long memberId, Long deliveryAddressId, String city, String street, String zipcode) {
        this.memberId = memberId;
        this.deliveryAddressId = deliveryAddressId;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
