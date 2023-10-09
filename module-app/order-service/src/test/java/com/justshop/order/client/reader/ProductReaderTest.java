package com.justshop.order.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.api.external.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.client.ProductServiceClient;
import com.justshop.order.client.response.OrderProductInfo;
import com.justshop.response.ApiResponse;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductReaderTest {

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private ProductReader productReader;

    @DisplayName("주문 상품 요청정보를 받아서 상품 서비스에서 주문된 상품 정보목록을 조회한다.")
    @Test
    void read_success() {
        // given
        Long productOptionId = 1L;
        Long productId = 1L;
        int price = 10000;
        int additionalPrice = 0;
        int stockQuantity = 100;
        List<OrderProductInfo> response = List.of(OrderProductInfo.builder()
                .productOptionId(productOptionId)
                .productId(productId)
                .price(price)
                .additionalPrice(additionalPrice)
                .stockQuantity(stockQuantity)
                .build());
        List<CreateOrderServiceRequest.OrderProductRequest> request = new ArrayList<>();

        given(productServiceClient.getOrderProductInfo(any(MultiValueMap.class)))
                .willReturn(ApiResponse.ok(response));

        // when
        List<OrderProductInfo> result = productReader.read(request);

        // then
        assertThat(result.get(0).getProductOptionId()).isEqualTo(productOptionId);
        assertThat(result.get(0).getProductId()).isEqualTo(productId);
        assertThat(result.get(0).getPrice()).isEqualTo(price);
        assertThat(result.get(0).getAdditionalPrice()).isEqualTo(additionalPrice);
        assertThat(result.get(0).getStockQuantity()).isEqualTo(stockQuantity);
    }

    @DisplayName("상품 서비스에서 주문된 상품 정보목록을 조회할 때 상품 서비스에 문제가 생기면 BusinessException이 발생한다.")
    @Test
    void read_fail1() {
        // given
        List<CreateOrderServiceRequest.OrderProductRequest> request = new ArrayList<>();

        given(productServiceClient.getOrderProductInfo(any(MultiValueMap.class)))
                .willThrow(FeignException.class);

        // when & then
        assertThatThrownBy(() -> productReader.read(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("상품 시스템 장애로 인하여 주문에 실패하였습니다.");
    }

    @DisplayName("상품 서비스에서 주문된 상품 정보목록을 조회할 때 null이 반환되면 BusinessException이 발생한다.")
    @Test
    void read_fail2() {
        // given
        List<CreateOrderServiceRequest.OrderProductRequest> request = new ArrayList<>();

        given(productServiceClient.getOrderProductInfo(any(MultiValueMap.class)))
                .willReturn(null);

        // when & then
        assertThatThrownBy(() -> productReader.read(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("주문 상품 조회에 실패하여 주문에 실패하였습니다.");
    }

}