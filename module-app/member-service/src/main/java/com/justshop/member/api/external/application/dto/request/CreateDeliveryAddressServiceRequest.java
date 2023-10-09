package com.justshop.member.api.external.application.dto.request;

import com.justshop.member.domain.entity.Address;
import com.justshop.member.domain.entity.DeliveryAddress;
import com.justshop.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateDeliveryAddressServiceRequest {

    private Long memberId; // 회원 ID
    private String city; // 주소 1
    private String street; // 주소 2
    private String zipcode; // 주소 3

    @Builder
    public CreateDeliveryAddressServiceRequest(Long memberId, String city, String street, String zipcode) {
        this.memberId = memberId;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public DeliveryAddress toBasicAddressEntity(Member member) {
        Address address = new Address(street, city, zipcode);
        return DeliveryAddress.builder()
                .basicYn(true)
                .member(member)
                .address(address)
                .build();
    }

    public DeliveryAddress toNormalAddressEntity(Member member) {
        Address address = new Address(street, city, zipcode);
        return DeliveryAddress.builder()
                .basicYn(false)
                .member(member)
                .address(address)
                .build();
    }
}
