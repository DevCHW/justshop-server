package com.justshop.member.api.external.application;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.api.external.application.dto.request.CreateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.request.UpdateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.response.DeliveryAddressResponse;
import com.justshop.member.domain.entity.Address;
import com.justshop.member.domain.entity.DeliveryAddress;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.DeliveryAddressRepository;
import com.justshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryAddressService {

    private final MemberRepository memberRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    /* 회원 배송지 추가 */
    @Transactional
    public Long create(CreateDeliveryAddressServiceRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByMemberId(request.getMemberId());
        if (deliveryAddresses.size() == 0) {
            return deliveryAddressRepository.save(request.toBasicAddressEntity(member)).getId();
        }
        return deliveryAddressRepository.save(request.toNormalAddressEntity(member)).getId();
    }

    /* 회원의 배송지 목록 조회 */
    public List<DeliveryAddressResponse> getAll(Long memberId) {
        return deliveryAddressRepository.findAllByMemberId(memberId).stream().map(deliveryAddress -> DeliveryAddressResponse.from(deliveryAddress))
                .collect(Collectors.toList());
    }

    /* 기본배송지로 변경 */
    @Transactional
    public void updateBasicAddress(Long memberId, Long deliveryAddressId) {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByMemberId(memberId);

        DeliveryAddress basicAddress = deliveryAddresses.stream().filter(deliveryAddress -> deliveryAddress.isBasicYn())
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND, "기본배송지를 찾을 수 없습니다."));
        DeliveryAddress targetAddress = deliveryAddresses.stream().filter(deliveryAddress -> deliveryAddress.getId() == deliveryAddressId)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND));

        basicAddress.changeBasicYn();
        targetAddress.changeBasicYn();
    }

    /* 회원 배송지 존재 여부 조회 */
    public Boolean getExists(Long memberId) {
        return deliveryAddressRepository.existsByMemberId(memberId);
    }

    /* 회원의 배송지 하나 조회 */
    public DeliveryAddressResponse getOne(Long memberId, Long deliveryAddressId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND));

        return DeliveryAddressResponse.from(deliveryAddress);
    }

    /* 회원의 배송지 삭제 */
    @Transactional
    public void delete(Long memberId, Long deliveryAddressId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND));

        if (deliveryAddress.isBasicYn()) {
            throw new BusinessException(ErrorCode.DELIVERY_ADDRESS_DELETE_FAIL, "기본배송지는 삭제할 수 없습니다.");
        }

        deliveryAddressRepository.delete(deliveryAddress);
    }

    /* 회원의 배송지 수정 */
    @Transactional
    public void editAddress(UpdateDeliveryAddressServiceRequest request) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND));

        Address address = new Address(request.getStreet(), request.getCity(), request.getZipcode());
        deliveryAddress.changeAddress(address);
    }
}
