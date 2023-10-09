package com.justshop.member.api.external.presentation;

import com.justshop.member.api.external.application.DeliveryAddressService;
import com.justshop.member.api.external.application.dto.response.DeliveryAddressResponse;
import com.justshop.member.api.external.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.member.api.external.presentation.dto.request.CreateDeliveryAddressRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdateDeliveryAddressRequest;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    /* 회원 배송지 추가 */
    @PostMapping("/{memberId}/delivery-addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createDeliveryAddress(@RequestBody @Valid CreateDeliveryAddressRequest request,
                                                   @PathVariable Long memberId) {
        deliveryAddressService.create(ServiceLayerDtoMapper.mapping(memberId, request));
        return ApiResponse.created();
    }

    /* 회원의 배송지 수정 */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{memberId}/delivery-addresses/{deliveryAddressId}")
    public ApiResponse editDeliveryAddress(@RequestBody UpdateDeliveryAddressRequest request,
                                           @PathVariable Long memberId,
                                           @PathVariable Long deliveryAddressId) {
        deliveryAddressService.editAddress(ServiceLayerDtoMapper.mapping(memberId, request, deliveryAddressId));
        return ApiResponse.noContent();
    }

    /* 회원의 배송지 삭제 */
    @DeleteMapping("/{memberId}/delivery-addresses/{deliveryAddressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse deleteDeliveryAddress(@PathVariable Long memberId, @PathVariable Long deliveryAddressId) {
        deliveryAddressService.delete(memberId, deliveryAddressId);
        return ApiResponse.noContent();
    }

    /* 기본배송지로 변경 */
    @PatchMapping("/{memberId}/delivery-addresses/{deliveryAddressId}/basic-yn")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse updateBasicAddress(@PathVariable Long memberId,
                                          @PathVariable Long deliveryAddressId) {
        deliveryAddressService.updateBasicAddress(memberId, deliveryAddressId);
        return ApiResponse.noContent();
    }

    /* 회원 배송지 존재 여부 조회 */
    @GetMapping("/{memberId}/delivery-addresses/exists")
    public ApiResponse<Boolean> existsDeliveryAddress(@PathVariable Long memberId) {
        Boolean response = deliveryAddressService.getExists(memberId);
        return ApiResponse.ok(response);
    }

    /* 회원의 배송지 하나 조회 */
    @GetMapping("/{memberId}/delivery-addresses/{deliveryAddressId}")
    public ApiResponse<DeliveryAddressResponse> getDeliveryAddress(@PathVariable Long memberId,
                                                                   @PathVariable Long deliveryAddressId) {
        DeliveryAddressResponse response = deliveryAddressService.getOne(memberId, deliveryAddressId);
        return ApiResponse.ok(response);
    }

    /* 회원의 배송지 목록 조회 */
    @GetMapping("/{memberId}/delivery-addresses")
    public ApiResponse<List<DeliveryAddressResponse>> getAllDeliveryAddress(@PathVariable Long memberId) {
        List<DeliveryAddressResponse> response = deliveryAddressService.getAll(memberId);
        return ApiResponse.ok(response);
    }

}
