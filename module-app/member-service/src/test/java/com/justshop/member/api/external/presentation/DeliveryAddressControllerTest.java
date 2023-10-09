package com.justshop.member.api.external.presentation;

import com.justshop.member.RestDocsSupport;
import com.justshop.member.api.external.application.DeliveryAddressService;
import com.justshop.member.api.external.application.dto.request.CreateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.request.UpdateDeliveryAddressServiceRequest;
import com.justshop.member.api.external.application.dto.response.DeliveryAddressResponse;
import com.justshop.member.api.external.presentation.dto.request.CreateDeliveryAddressRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdateDeliveryAddressRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeliveryAddressControllerTest extends RestDocsSupport {

    private DeliveryAddressService deliveryAddressService = Mockito.mock(DeliveryAddressService.class);

    @Override
    protected Object initController() {
        return new DeliveryAddressController(deliveryAddressService);
    }

    @DisplayName("배송지 정보를 받아서 회원의 배송지를 추가할 수 있다.")
    @Test
    void createDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        CreateDeliveryAddressRequest request = CreateDeliveryAddressRequest.builder()
                .city("Test City")
                .street("Test Street")
                .zipcode("Test Zipcode")
                .build();

        given(deliveryAddressService.create(any(CreateDeliveryAddressServiceRequest.class)))
                .willReturn(anyLong());

        // when & then
        mockMvc.perform(
                        post("/api/v1/members/{memberId}/delivery-addresses", memberId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andDo(document("member-delivery-addresses-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                requestFields(
                                        fieldWithPath("city").type(JsonFieldType.STRING).description("도시"),
                                        fieldWithPath("street").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("zipcode").type(JsonFieldType.STRING).description("우편번호")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }


    @DisplayName("회원 ID, 배송지 ID, 수정할 배송지 정보를 받아서 배송지 정보를 수정할 수 있다.")
    @Test
    void editDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        Long deliveryAddressServiceId = 1L;
        UpdateDeliveryAddressRequest request = UpdateDeliveryAddressRequest.builder()
                .city("Test City")
                .street("Test Street")
                .zipcode("Test Zipcode")
                .build();

        willDoNothing().given(deliveryAddressService).editAddress(any(UpdateDeliveryAddressServiceRequest.class));

        // when & then
        mockMvc.perform(
                        patch("/api/v1/members/{memberId}/delivery-addresses/{deliveryAddressId}", memberId, deliveryAddressServiceId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andDo(document("member-delivery-addresses-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID"),
                                        parameterWithName("deliveryAddressId").description("배송지 ID")
                                ),
                                requestFields(
                                        fieldWithPath("city").type(JsonFieldType.STRING).description("도시"),
                                        fieldWithPath("street").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("zipcode").type(JsonFieldType.STRING).description("우편번호")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }

    @DisplayName("회원 ID, 배송지 ID, 수정할 배송지 정보를 받아서 배송지를 삭제할 수 있다.")
    @Test
    void deleteDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        Long deliveryAddressServiceId = 1L;

        willDoNothing().given(deliveryAddressService).delete(anyLong(), anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/v1/members/{memberId}/delivery-addresses/{deliveryAddressId}", memberId, deliveryAddressServiceId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andDo(document("member-delivery-addresses-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID"),
                                        parameterWithName("deliveryAddressId").description("배송지 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }

    @DisplayName("회원 ID, 배송지 ID, 수정할 배송지 정보를 받아서 기본배송지로 변경할 수 있다.")
    @Test
    void updateBasicAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        Long deliveryAddressServiceId = 1L;

        willDoNothing().given(deliveryAddressService).updateBasicAddress(anyLong(), anyLong());

        // when & then
        mockMvc.perform(
                        patch("/api/v1/members/{memberId}/delivery-addresses/{deliveryAddressId}/basic-yn", memberId, deliveryAddressServiceId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andDo(document("member-delivery-addresses-update-basic-yn",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID"),
                                        parameterWithName("deliveryAddressId").description("배송지 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }

    @DisplayName("회원 ID를 입력받아서 회원의 배송지가 존재하는지 여부를 조회한다.")
    @Test
    void existsDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;

        given(deliveryAddressService.getExists(anyLong())).willReturn(true);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/{memberId}/delivery-addresses/exists", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value(true))
                .andDo(document("member-delivery-addresses-get-exists",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("회원 배송지 존재 여부")
                                )
                        )
                );
    }

    @DisplayName("회원 ID, 배송지 ID를 입력받아서 회원의 배송지 하나를 조회한다.")
    @Test
    void getDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        Long deliveryAddressId = 1L;
        DeliveryAddressResponse response = DeliveryAddressResponse.builder()
                .deliveryAddressId(deliveryAddressId)
                .city("Test City")
                .street("Test Street")
                .zipcode("Test Zipcode")
                .basicYn(true)
                .build();

        given(deliveryAddressService.getOne(anyLong(), anyLong()))
                .willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/{memberId}/delivery-addresses/{deliveryAddressId}", memberId, deliveryAddressId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.city").value(response.getCity()))
                .andExpect(jsonPath("$.data.street").value(response.getStreet()))
                .andExpect(jsonPath("$.data.zipcode").value(response.getZipcode()))
                .andExpect(jsonPath("$.data.basicYn").value(response.getBasicYn()))
                .andDo(document("member-delivery-addresses-get-one",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID"),
                                        parameterWithName("deliveryAddressId").description("배송지 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원의 배송지 정보"),
                                        fieldWithPath("data.deliveryAddressId").type(JsonFieldType.NUMBER).description("배송지 ID"),
                                        fieldWithPath("data.city").type(JsonFieldType.STRING).description("도시"),
                                        fieldWithPath("data.street").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("data.zipcode").type(JsonFieldType.STRING).description("우편번호"),
                                        fieldWithPath("data.basicYn").type(JsonFieldType.BOOLEAN).description("기본배송지 여부")
                                )
                        )
                );
    }

    @DisplayName("회원 ID를 받아서 회원의 배송지 목록을 조회한다.")
    @Test
    void getAllDeliveryAddress_success() throws Exception {
        // given
        Long memberId = 1L;
        DeliveryAddressResponse response1 = DeliveryAddressResponse.builder()
                .deliveryAddressId(1L)
                .city("Test City1")
                .street("Test Street1")
                .zipcode("Test Zipcode1")
                .basicYn(true)
                .build();
        DeliveryAddressResponse response2 = DeliveryAddressResponse.builder()
                .deliveryAddressId(2L)
                .city("Test City2")
                .street("Test Street2")
                .zipcode("Test Zipcode2")
                .basicYn(false)
                .build();
        List<DeliveryAddressResponse> response = List.of(response1, response2);

        given(deliveryAddressService.getAll(anyLong()))
                .willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/{memberId}/delivery-addresses", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("member-delivery-addresses-get-all",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("회원의 배송지 정보 목록"),
                                        fieldWithPath("data[].deliveryAddressId").type(JsonFieldType.NUMBER).description("배송지 ID"),
                                        fieldWithPath("data[].city").type(JsonFieldType.STRING).description("도시"),
                                        fieldWithPath("data[].street").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("data[].zipcode").type(JsonFieldType.STRING).description("주소"),
                                        fieldWithPath("data[].basicYn").type(JsonFieldType.BOOLEAN).description("기본배송지 여부")
                                )
                        )
                );
    }
}