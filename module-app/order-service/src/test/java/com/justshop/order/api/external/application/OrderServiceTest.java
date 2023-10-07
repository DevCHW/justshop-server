package com.justshop.order.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.DataFactoryUtil;
import com.justshop.order.api.external.application.OrderService;
import com.justshop.order.api.external.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.api.external.application.dto.request.CreateOrderServiceRequest.OrderProductRequest;
import com.justshop.order.client.reader.CouponReader;
import com.justshop.order.client.reader.DeliveryReader;
import com.justshop.order.client.reader.MemberReader;
import com.justshop.order.client.reader.ProductReader;
import com.justshop.order.client.response.DeliveryResponse;
import com.justshop.order.client.response.MemberResponse;
import com.justshop.order.client.response.OrderProductInfo;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.enums.OrderStatus;
import com.justshop.order.domain.repository.OrderRepository;
import com.justshop.order.infrastructure.kafka.producer.OrderCreateProducer;
import com.justshop.order.infrastructure.kafka.producer.OrderUpdateProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    private ProductReader productReader;

    @MockBean
    private MemberReader memberReader;

    @MockBean
    private CouponReader couponReader;

    @MockBean
    private DeliveryReader deliveryReader;

    @MockBean
    private OrderCreateProducer orderCreateProducer;

    @MockBean
    private OrderUpdateProducer orderUpdateProducer;

    @DisplayName("주문에 필요한 정보를 받아서 주문을 생성한다.")
    @Test
    void order_success() {
        // given
        List<OrderProductRequest> orderProducts = generateOrderProducts();
        CreateOrderServiceRequest request = CreateOrderServiceRequest.builder()
                .memberId(1L)
                .usePoint(1000L)
                .orderProducts(orderProducts)
                .couponId(2L)
                .build();

        MemberResponse memberResponse = generateMemberResponse();
        List<OrderProductInfo> orderProductInfoList = new ArrayList<>();

        given(memberReader.read(anyLong()))
                .willReturn(memberResponse);

        given(productReader.read(any(List.class)))
                .willReturn(orderProductInfoList);

        // when
        Long savedOrderId = orderService.order(request);

        // then
        assertThat(savedOrderId).isNotNull();
    }

    @DisplayName("주문을 생성할 때 주문자의 보유 포인트가 부족할 시 BusinessException 이 발생한다.")
    @Test
    void order_fail_not_enough_point() {
        // given
        List<OrderProductRequest> orderProducts = generateOrderProducts();
        CreateOrderServiceRequest request = CreateOrderServiceRequest.builder()
                .memberId(1L)
                .usePoint(1000L)
                .orderProducts(orderProducts)
                .couponId(2L)
                .build();

        MemberResponse memberResponse = MemberResponse.builder()
                .memberId(1L)
                .email("testEmail")
                .name("testName")
                .nickname("testNickname")
                .point(0)
                .birthday(LocalDate.of(1997, 1, 3))
                .memberRole("USER")
                .gender("MAN")
                .status("ACTIVE")
                .build();;
        List<OrderProductInfo> orderProductInfoList = new ArrayList<>();

        given(memberReader.read(anyLong()))
                .willReturn(memberResponse);

        given(productReader.read(any(List.class)))
                .willReturn(orderProductInfoList);

        // when & then
        assertThatThrownBy(() -> orderService.order(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("포인트가 부족합니다.");
    }

    @DisplayName("주문을 생성할 때 주문 상품의 재고가 부족할 시 BusinessException 이 발생한다.")
    @Test
    void order_fail_not_enough_stock() {
        // given
        List<OrderProductRequest> orderProducts = generateOrderProducts();
        CreateOrderServiceRequest request = CreateOrderServiceRequest.builder()
                .memberId(1L)
                .usePoint(1000L)
                .orderProducts(orderProducts)
                .couponId(2L)
                .build();

        MemberResponse memberResponse = generateMemberResponse();
        List<OrderProductInfo> orderProductInfoList = generateOrderProductInfoList();

        given(memberReader.read(anyLong()))
                .willReturn(memberResponse);

        given(productReader.read(any(List.class)))
                .willReturn(orderProductInfoList);

        // when & then
        assertThatThrownBy(() -> orderService.order(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @DisplayName("주문 ID로 주문을 취소할 수 있다.")
    @Test
    void cancel_success() {
        // given
        Order order = orderRepository.save(DataFactoryUtil.generateOrder());

        DeliveryResponse response = new DeliveryResponse();
        given(deliveryReader.read(anyLong())).willReturn(response);

        // when
        Long orderId = orderService.cancel(order.getId());
        Order result = orderRepository.findById(orderId).get();

        // then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    // 회원 정보 Response 생성
    private List<OrderProductRequest> generateOrderProducts() {
        OrderProductRequest orderProductRequest1 = generateOrderProductRequest(1L, 1L);
        OrderProductRequest orderProductRequest2 = generateOrderProductRequest(1L, 2L);
        OrderProductRequest orderProductRequest3 = generateOrderProductRequest(1L, 3L);
        OrderProductRequest orderProductRequest4 = generateOrderProductRequest(1L, 13L);
        return List.of(orderProductRequest1, orderProductRequest2, orderProductRequest3, orderProductRequest4);
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

    // 주문 상품 가격정보 생성
    private List<OrderProductInfo> generateOrderProductInfoList() {
        OrderProductInfo orderProductInfo1 = OrderProductInfo.builder()
                .productOptionId(1L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(10)
                .build();
        OrderProductInfo orderProductInfo2 = OrderProductInfo.builder()
                .productOptionId(1L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(10)
                .build();
        OrderProductInfo orderProductInfo3 = OrderProductInfo.builder()
                .productOptionId(1L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(10)
                .build();
        return List.of(orderProductInfo1, orderProductInfo2, orderProductInfo3);
    }

}