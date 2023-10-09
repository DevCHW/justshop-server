package com.justshop.member.api.external.application.dto.response;

import com.justshop.member.domain.entity.DeliveryAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryAddressResponse {

    private Long deliveryAddressId; // 배송지 ID
    private Boolean basicYn; // 기본주소 여부
    private String city; // 주소 1
    private String street; // 주소 2
    private String zipcode; // 주소 3

    @Builder
    public DeliveryAddressResponse(Long deliveryAddressId, Boolean basicYn, String city, String street, String zipcode) {
        this.deliveryAddressId = deliveryAddressId;
        this.basicYn = basicYn;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public static DeliveryAddressResponse from(DeliveryAddress deliveryAddress) {
        return DeliveryAddressResponse.builder()
                .deliveryAddressId(deliveryAddress.getId())
                .basicYn(deliveryAddress.isBasicYn())
                .city(deliveryAddress.getAddress().getCity())
                .street(deliveryAddress.getAddress().getStreet())
                .zipcode(deliveryAddress.getAddress().getZipcode())
                .build();
    }
}
