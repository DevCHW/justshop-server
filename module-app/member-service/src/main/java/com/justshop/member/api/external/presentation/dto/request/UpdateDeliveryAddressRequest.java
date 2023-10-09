package com.justshop.member.api.external.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateDeliveryAddressRequest {

    @NotBlank(message = "주소는 필수입니다.")
    private String city; // 주소 1

    @NotBlank(message = "주소는 필수입니다.")
    private String street; // 주소 2

    @NotBlank(message = "주소는 필수입니다.")
    private String zipcode; // 주소 3

    @Builder
    public UpdateDeliveryAddressRequest(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
