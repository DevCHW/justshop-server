package com.justshop.order.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.order.OrderUpdate;
import com.justshop.order.api.external.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.api.external.application.dto.response.OrderResponse;
import com.justshop.order.client.reader.CouponReader;
import com.justshop.order.client.reader.DeliveryReader;
import com.justshop.order.client.reader.MemberReader;
import com.justshop.order.client.reader.ProductReader;
import com.justshop.order.client.response.DeliveryResponse;
import com.justshop.order.infrastructure.kafka.producer.OrderCreateProducer;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.order.client.response.MemberResponse;
import com.justshop.order.client.response.OrderProductInfo;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.repository.OrderRepository;
import com.justshop.order.infrastructure.kafka.producer.OrderUpdateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.justshop.core.error.ErrorCode.*;
import static com.justshop.order.domain.entity.enums.OrderStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreateProducer orderCreateProducer;
    private final OrderUpdateProducer orderUpdateProducer;
    private final DeliveryReader deliveryReader;
    private final MemberReader memberReader;
    private final ProductReader productReader;
    private final CouponReader couponReader;

    /* 주문 */
    @Transactional
    public Long order(CreateOrderServiceRequest request) {
        // 주문자 정보 조회, 포인트 체크
        MemberResponse memberInfo = memberReader.read(request.getMemberId());
        pointCheck(request.getUsePoint(), memberInfo.getPoint());

        // 쿠폰 서비스에서 쿠폰 할인율 조회
        int couponDiscountRate = couponReader.read(request.getCouponId());

        // 주문상품 조회하여 재고체크, 주문상품 총 가격계산
        List<OrderProductInfo> orderProductInfoList = productReader.read(request.getOrderProducts());
        Long totalPrice = getTotalPriceAndStockCheck(request.getOrderProducts(), orderProductInfoList);

        // 최종결제금액 계산
        Long payAmount = calculatePrice(totalPrice, request.getUsePoint(), couponDiscountRate);

        // 주문 생성
        Long savedOrderId = orderRepository.save(request.toEntity(totalPrice, payAmount)).getId();

        // 주문 생성 이벤트 메세지 발행
        OrderCreate message = CreateOrderCreateEventMessage(request, payAmount, savedOrderId);
        orderCreateProducer.send(message);

        return savedOrderId;
    }

    // 보유포인트 체크
    private void pointCheck(Long useAmount, int memberPoint) {
        if (memberPoint < useAmount) {
            log.error(NOT_ENOUGH_STOCK.getMessage());
            throw new BusinessException(NOT_ENOUGH_POINT, "포인트가 부족합니다.");
        }
    }

    // 재고 체크, 가격 계산
    private Long getTotalPriceAndStockCheck(List<CreateOrderServiceRequest.OrderProductRequest> orderProducts, List<OrderProductInfo> productPrices) {
        Map<Long, Integer> orderQuantityMap = orderProducts.stream()
                .collect(Collectors.toMap(op -> op.getProductOptionId(), op -> op.getQuantity()));

        AtomicLong totalPrice = new AtomicLong();
        productPrices.forEach(p -> {
            if (p.getStockQuantity() < orderQuantityMap.get(p.getProductOptionId())) {
                throw new BusinessException(NOT_ENOUGH_STOCK, "재고가 부족합니다.");
            }
            int orderPrice = (p.getPrice() + p.getAdditionalPrice()) * orderQuantityMap.get(p.getProductOptionId());
            totalPrice.addAndGet(orderPrice);
        });
        return totalPrice.get();
    }

    // 최종결제금액 계산
    private long calculatePrice(Long totalPrice, Long usePoint, int couponDiscountRate) {
        return (totalPrice - usePoint) * (couponDiscountRate / 100);
    }

    // 주문생성 Event Message 생성
    private OrderCreate CreateOrderCreateEventMessage(CreateOrderServiceRequest request, Long payAmount, Long orderId) {
        List<OrderCreate.OrderQuantity> orderQuantities = request.getOrderProducts().stream()
                .map(op -> new OrderCreate.OrderQuantity(op.getProductOptionId(), op.getQuantity()))
                .collect(Collectors.toList());
        return OrderCreate.builder()
                .orderId(orderId)
                .memberId(request.getMemberId())
                .couponId(request.getCouponId())
                .usePoint(request.getUsePoint())
                .payAmount(payAmount)
                .orderQuantities(orderQuantities)
                .build();
    }

    /* 주문 상세조회 */
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findWithOrderProductsById(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));

        // TODO: OrderResponse 작성
        return OrderResponse.from(order);
    }

    /* 주문 취소 */
    @Transactional
    public Long cancel(Long orderId) {
        Order order = orderRepository.findWithOrderProductsById(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));

        // 주문 상태가 처리완료 상태면 취소불가
        if (order.getStatus().equals(COMPLETE)) {
            throw new BusinessException(ORDER_CANCEL_FAIL, "처리가 완료된 주문건은 취소할 수 없습니다.");
        }

        // 배송정보 조회해서 배송중이라면 취소불가
        DeliveryResponse deliveryResponse = deliveryReader.read(orderId);
        // TODO : 배송중 또는 배송완료일 시 취소 처리 로직 ....

        // TODO : 결제정보 조회

        // TODO : 결제상태가 결제완료된 건이라면 결제 시스템에서 결제 취소 진행

        // 주문 상태 취소로 변경
        order.cancel();
        log.info("주문이 취소되었습니다. Order ID : {}", order.getId());

        // 이벤트 메세지 발행
        OrderUpdate orderUpdate = new OrderUpdate(order.getId(), order.getStatus().name());
        orderUpdateProducer.send(orderUpdate);

        // ID 반환
        return order.getId();
    }

}
