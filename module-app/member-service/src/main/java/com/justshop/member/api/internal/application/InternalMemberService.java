package com.justshop.member.api.internal.application;

import com.justshop.client.dto.MemberResponse;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.api.external.application.dto.response.DeliveryAddressResponse;
import com.justshop.member.domain.entity.DeliveryAddress;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.justshop.core.error.ErrorCode.DELIVERY_NOT_FOUND;
import static com.justshop.core.error.ErrorCode.MEMBER_NOT_FOUND;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalMemberService {

    private final MemberRepository memberRepository;

    /* 회원 정보 조회 */
    public MemberResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findWithDeliveryAddressById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        DeliveryAddress deliveryAddress = member.getAddress().stream()
                .filter(address -> address.isBasicYn())
                .findFirst().orElseThrow(() -> new BusinessException(DELIVERY_NOT_FOUND));

        MemberResponse.DeliveryAddressResponse address = MemberResponse.DeliveryAddressResponse.builder()
                .city(deliveryAddress.getAddress().getCity())
                .street(deliveryAddress.getAddress().getStreet())
                .zipcode(deliveryAddress.getAddress().getZipcode())
                .build();

        return MemberResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .birthday(member.getBirthday())
                .memberRole(member.getMemberRole().toString())
                .gender(member.getGender().toString())
                .status(member.getStatus().toString())
                .address(address)
                .build();
    }

}
