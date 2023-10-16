package com.justshop.member.api.external.application;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.DataFactoryUtil;
import com.justshop.member.api.external.application.dto.request.CreateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.request.UpdateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.response.DeliveryAddressResponse;
import com.justshop.member.domain.entity.Address;
import com.justshop.member.domain.entity.DeliveryAddress;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.DeliveryAddressRepository;
import com.justshop.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
class DeliveryAddressServiceTest {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 배송지를 생성할 때 등록된 배송지가 없다면 기본배송지로 생성한다.")
    @Test
    void create_success1() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());

        String city = "Test City";
        String street = "Test Street";
        String zipcode = "Test Zipcode";
        Long memberId = savedMember.getId();
        CreateDeliveryAddressServiceRequest request = CreateDeliveryAddressServiceRequest.builder()
                .memberId(memberId)
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();

        // when
        Long savedId = deliveryAddressService.create(request);
        DeliveryAddress result = deliveryAddressRepository.findById(savedId).get();

        // then
        assertThat(result.getAddress().getCity()).isEqualTo(city);
        assertThat(result.getAddress().getZipcode()).isEqualTo(zipcode);
        assertThat(result.getAddress().getStreet()).isEqualTo(street);
        assertThat(result.getId()).isEqualTo(savedId);
        assertThat(result.isBasicYn()).isTrue();
    }

    @DisplayName("회원의 배송지를 생성할 때 등록된 배송지가 있다면 일반배송지로 생성한다.")
    @Test
    void create_success2() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address = new Address("Street", "City", "Zipcode");
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .member(savedMember)
                .basicYn(true)
                .address(address)
                .build();
        deliveryAddressRepository.save(deliveryAddress);

        String city = "Test City";
        String street = "Test Street";
        String zipcode = "Test Zipcode";
        Long memberId = savedMember.getId();
        CreateDeliveryAddressServiceRequest request = CreateDeliveryAddressServiceRequest.builder()
                .memberId(memberId)
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();

        // when
        Long savedId = deliveryAddressService.create(request);
        DeliveryAddress result = deliveryAddressRepository.findById(savedId).get();

        // then
        assertThat(result.getAddress().getCity()).isEqualTo(city);
        assertThat(result.getAddress().getZipcode()).isEqualTo(zipcode);
        assertThat(result.getAddress().getStreet()).isEqualTo(street);
        assertThat(result.getId()).isEqualTo(savedId);
        assertThat(result.isBasicYn()).isFalse();
    }

    @DisplayName("회원 ID를 받아서 등록된 배송지 목록 정보를 조회한다.")
    @Test
    void getAll_success() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address1 = new Address("Street1", "City1", "Zipcode1");
        Address address2 = new Address("Street2", "City2", "Zipcode2");
        Address address3 = new Address("Street3", "City3", "Zipcode3");
        DeliveryAddress deliveryAddress1 = generateDeliveryAddress(savedMember, address1, true);
        DeliveryAddress deliveryAddress2 = generateDeliveryAddress(savedMember, address2, false);
        DeliveryAddress deliveryAddress3 = generateDeliveryAddress(savedMember, address3, false);

        deliveryAddressRepository.saveAll(List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3));

        Long memberId = savedMember.getId();
        // when
        List<DeliveryAddressResponse> result = deliveryAddressService.getAll(memberId);

        // then
        assertThat(result).hasSize(3)
                .extracting("basicYn", "street", "city", "zipcode")
                .containsExactlyInAnyOrder(
                        tuple(true, "Street1", "City1", "Zipcode1"),
                        tuple(false, "Street2", "City2", "Zipcode2"),
                        tuple(false, "Street3", "City3", "Zipcode3")
                );
    }

    @DisplayName("회원 ID, 배송지 ID를 받아서 기본배송지로 변경한다.")
    @Test
    void updateBasicAddress_success() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address1 = new Address("Street1", "City1", "Zipcode1");
        Address address2 = new Address("Street2", "City2", "Zipcode2");
        Address address3 = new Address("Street3", "City3", "Zipcode3");
        DeliveryAddress deliveryAddress1 = generateDeliveryAddress(savedMember, address1, true);
        DeliveryAddress deliveryAddress2 = generateDeliveryAddress(savedMember, address2, false);
        DeliveryAddress deliveryAddress3 = generateDeliveryAddress(savedMember, address3, false);

        List<DeliveryAddress> savedDeliveryAddresses =
                deliveryAddressRepository.saveAll(List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3));

        Long memberId = savedMember.getId();
        Long targetId = savedDeliveryAddresses.get(1).getId();

        // when
        deliveryAddressService.updateBasicAddress(memberId, targetId);

        // then
        assertThat(savedDeliveryAddresses.get(0).isBasicYn()).isFalse();
        assertThat(savedDeliveryAddresses.get(1).isBasicYn()).isTrue();
    }

    @DisplayName("회원 ID를 받아서 회원의 배송지 존재여부를 조회한다. 존재한다면 true를 반환한다.")
    @Test
    void getExists_success_return_true() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address1 = new Address("Street1", "City1", "Zipcode1");
        Address address2 = new Address("Street2", "City2", "Zipcode2");
        Address address3 = new Address("Street3", "City3", "Zipcode3");
        DeliveryAddress deliveryAddress1 = generateDeliveryAddress(savedMember, address1, true);
        DeliveryAddress deliveryAddress2 = generateDeliveryAddress(savedMember, address2, false);
        DeliveryAddress deliveryAddress3 = generateDeliveryAddress(savedMember, address3, false);

        List<DeliveryAddress> savedDeliveryAddresses =
                deliveryAddressRepository.saveAll(List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3));

        Long memberId = savedMember.getId();

        // when
        Boolean exists = deliveryAddressService.getExists(memberId);

        // then
        assertThat(exists).isTrue();
    }

    @DisplayName("회원 ID를 받아서 회원의 배송지 존재여부를 조회한다. 존재하지 않는다면 false를 반환한다.")
    @Test
    void getExists_success_return_false() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Long memberId = savedMember.getId();

        // when
        Boolean exists = deliveryAddressService.getExists(memberId);

        // then
        assertThat(exists).isFalse();
    }

    @DisplayName("회원 ID, 배송지 ID를 받아서 배송지를 상세조회한다.")
    @Test
    void getOne_success() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address = new Address("Test Street", "Test City", "Test Zipcode");
        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.save(generateDeliveryAddress(savedMember, address, true));

        Long memberId = savedMember.getId();
        Long deliveryAddressId = savedDeliveryAddress.getId();
        // when
        DeliveryAddressResponse result = deliveryAddressService.getOne(memberId, deliveryAddressId);

        // then
        assertThat(result.getDeliveryAddressId()).isEqualTo(deliveryAddressId);
        assertThat(result.getBasicYn()).isEqualTo(savedDeliveryAddress.isBasicYn());
        assertThat(result.getCity()).isEqualTo(savedDeliveryAddress.getAddress().getCity());
        assertThat(result.getStreet()).isEqualTo(savedDeliveryAddress.getAddress().getStreet());
        assertThat(result.getZipcode()).isEqualTo(savedDeliveryAddress.getAddress().getZipcode());
    }

    @DisplayName("회원 ID, 배송지 ID를 받아서 배송지를 상세조회한다.")
    @Test
    void getOne_fail() {
        Long memberId = 1L;
        Long deliveryAddressId = 1L;

        // when & then
        assertThatThrownBy(() -> deliveryAddressService.getOne(memberId, deliveryAddressId))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_DELIVERY_ADDRESS_NOT_FOUND.getMessage());
    }

    @DisplayName("회원 ID, 배송지 ID를 받아서 배송지를 삭제한다.")
    @Test
    void delete_success() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address = new Address("Test Street", "Test City", "Test Zipcode");
        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.save(generateDeliveryAddress(savedMember, address, false));

        Long memberId = savedMember.getId();
        Long deliveryAddressId = savedDeliveryAddress.getId();
        // when
        deliveryAddressService.delete(memberId, deliveryAddressId);
        DeliveryAddress result = deliveryAddressRepository.findById(deliveryAddressId).orElse(null);

        // then
        assertThat(result).isNull();
    }

    @DisplayName("기본배송지를 삭제하려고 하면 BusinessException이 발생한다.")
    @Test
    void delete_fail() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address = new Address("Test Street", "Test City", "Test Zipcode");
        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.save(generateDeliveryAddress(savedMember, address, true));

        Long memberId = savedMember.getId();
        Long deliveryAddressId = savedDeliveryAddress.getId();

        // when & then
        assertThatThrownBy(() -> deliveryAddressService.delete(memberId, deliveryAddressId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("기본배송지는 삭제할 수 없습니다.");
    }
    
    @DisplayName("수정하려는 주소를 받아서 기존에 등록된 배송지의 주소를 수정한다.")
    @Test
    void edit_success() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());
        Address address = new Address("Test Street", "Test City", "Test Zipcode");
        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.save(generateDeliveryAddress(savedMember, address, true));

        Long memberId = savedMember.getId();
        Long deliveryAddressId = savedDeliveryAddress.getId();

        String updateStreet = "Update Street";
        String updateCity = "Update City";
        String updateZipcode = "Update Zipcode";

        UpdateDeliveryAddressServiceRequest request = UpdateDeliveryAddressServiceRequest.builder()
                .memberId(memberId)
                .deliveryAddressId(deliveryAddressId)
                .city(updateCity)
                .street(updateStreet)
                .zipcode(updateZipcode)
                .build();

        // when
        deliveryAddressService.editAddress(request);
    
        // then
        assertThat(savedDeliveryAddress.getAddress().getStreet()).isEqualTo(updateStreet);
        assertThat(savedDeliveryAddress.getAddress().getCity()).isEqualTo(updateCity);
        assertThat(savedDeliveryAddress.getAddress().getZipcode()).isEqualTo(updateZipcode);
    }

    /* Factory Method */
    private DeliveryAddress generateDeliveryAddress(Member member, Address address, boolean basicYn) {
        return DeliveryAddress.builder()
                .member(member)
                .basicYn(basicYn)
                .address(address)
                .build();
    }

}