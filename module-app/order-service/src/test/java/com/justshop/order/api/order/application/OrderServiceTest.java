package com.justshop.order.api.order.application;

import com.justshop.order.DataFactoryUtil;
import com.justshop.order.api.order.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.api.order.application.dto.request.CreateOrderServiceRequest.OrderProductRequest;
import com.justshop.order.client.DeliveryServiceClient;
import com.justshop.order.client.MemberServiceClient;
import com.justshop.order.client.ProductServiceClient;
import com.justshop.order.client.response.MemberResponse;
import com.justshop.order.client.response.ProductPriceResponse;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.enums.OrderStatus;
import com.justshop.order.domain.repository.OrderRepository;
import com.justshop.order.infrastructure.kafka.producer.OrderCreateProducer;
import com.justshop.order.infrastructure.kafka.producer.OrderUpdateProducer;
import com.justshop.response.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private ProductServiceClient productServiceClient;

    @MockBean
    private MemberServiceClient memberServiceClient;

    @MockBean
    private DeliveryServiceClient deliveryServiceClient;

    @MockBean
    private OrderCreateProducer orderCreateProducer;

    @MockBean
    private OrderUpdateProducer orderUpdateProducer;

    @DisplayName("주문에 필요한 정보를 받아서 주문을 생성한다.")
    @Test
    void orderSuccess() {
        // given
        OrderProductRequest orderProductRequest1 = generateOrderProductRequest(1L, 1L);
        OrderProductRequest orderProductRequest2 = generateOrderProductRequest(1L, 2L);
        OrderProductRequest orderProductRequest3 = generateOrderProductRequest(1L, 3L);
        OrderProductRequest orderProductRequest4 = generateOrderProductRequest(1L, 13L);
        List<OrderProductRequest> orderProducts = List.of(orderProductRequest1, orderProductRequest2, orderProductRequest3, orderProductRequest4);

        CreateOrderServiceRequest request = CreateOrderServiceRequest.builder()
                .memberId(1L)
                .usePoint(1000L)
                .orderProducts(orderProducts)
                .couponId(2L)
                .build();

        MemberResponse memberResponse = generateMemberResponse();
        List<ProductPriceResponse> productPriceResponses = new ArrayList<>();

        MultiValueMap<String, Long> productOptionIds = new LinkedMultiValueMap<>();
        request.getOrderProducts().forEach(op ->
                productOptionIds.add("productOptionId", op.getProductOptionId())
        );

        given(memberServiceClient.getMemberInfo(anyLong()))
                .willReturn(ApiResponse.ok(memberResponse));

        given(productServiceClient.getOrderPriceInfo(productOptionIds))
                .willReturn(ApiResponse.ok(productPriceResponses));

        // when
        Long savedOrderId = orderService.order(request);

        // then
        assertThat(savedOrderId).isNotNull();
    }

    // 회원 정보 Response 생성
    private MemberResponse generateMemberResponse() {
        return MemberResponse.builder()
                .memberId(1L)
                .email("testEmail")
                .name("testName")
                .nickname("testNickname")
                .point(1000)
                .birthday(LocalDate.of(1997, 1, 3))
                .memberRole("USER")
                .gender("MAN")
                .status("ACTIVE")
                .build();
    }

    // 주문상품 Request 생성
    private OrderProductRequest generateOrderProductRequest(Long productId, Long productOptionId) {
        return OrderProductRequest.builder()
                .productId(productId)
                .productOptionId(productOptionId)
                .quantity(20)
                .build();
    }

    @DisplayName("주문 ID로 주문을 취소할 수 있다.")
    @Test
    void cancel() {
        // given
        Order order = orderRepository.save(DataFactoryUtil.generateOrder());

        // when
        Long orderId = orderService.cancel(order.getId());
        Order result = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);

        // then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

}