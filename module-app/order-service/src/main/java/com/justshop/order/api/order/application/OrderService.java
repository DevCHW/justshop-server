package com.justshop.order.api.order.application;

import com.justshop.exception.BusinessException;
import com.justshop.order.api.order.application.dto.request.CreateOrderServiceRequest;
import com.justshop.order.api.order.application.dto.response.OrderResponse;
import com.justshop.order.api.order.infrastructure.kafka.KafkaProducer;
import com.justshop.order.api.order.infrastructure.kafka.dto.OrderCreate;
import com.justshop.order.client.MemberServiceClient;
import com.justshop.order.client.ProductServiceClient;
import com.justshop.order.client.response.MemberResponse;
import com.justshop.order.client.response.ProductPriceResponse;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.OrderProduct;
import com.justshop.order.domain.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.justshop.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;
    private final ProductServiceClient productServiceClient;
    private final KafkaProducer kafkaProducer;

    // 주문 TODO: 코드 리팩토링.....하기..
    @Transactional
    public void order(CreateOrderServiceRequest request) {
        // 주문자 정보 조회. 조회 실패시 익셉션
        MemberResponse memberInfo = null;
        try {
            memberInfo = memberServiceClient.getMemberInfo(request.getMemberId()).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "회원 시스템 장애로 인하여 주문에 실패하였습니다.");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "주문자 정보 조회에 실패하여 주문에 실패하였습니다.");
        }

        // 주문자한테 사용 포인트 이상 있는지 확인, 사용 포인트보다 보유 포인트가 적다면 익셉션
        if (memberInfo.getPoint() < request.getUsePoint()) {
            log.error(NOT_ENOUGH_STOCK.getDescription());
            throw new BusinessException(NOT_ENOUGH_POINT, "포인트가 부족합니다.");
        }

        // 사용될 쿠폰이 있다면 쿠폰서비스에서 쿠폰 조회
        int couponDiscountRate = 0;
        if (!Objects.isNull(request.getCouponId())) {
            log.info("사용될 쿠폰이 있습니다. 사용될 쿠폰ID={}", request.getCouponId());
            // Feign Client로 쿠폰서비스에서 쿠폰을 조회한다.
            // 쿠폰을 찾지 못한다면 익셉션
            // 쿠폰 보유자가 요청된 사용자가 맞는지 확인한다. 다르다면 익셉션
            // 쿠폰의 할인율을 couponDiscountRate 변수에 담기.
        }

        // 주문한 상품 옵션 아이디 리스트 추출
        MultiValueMap<String, Long> productOptionIds = new LinkedMultiValueMap<>();
        request.getOrderProducts().forEach(op ->
                productOptionIds.add("productOptionId", op.getProductOptionId())
        );

        // 주문한 상품목록의 가격정보 조회
        List<ProductPriceResponse> productPrices = null;
        try {
            productPrices = productServiceClient.getOrderPriceInfo(productOptionIds).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "상품 시스템 장애로 인하여 주문에 실패하였습니다.");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "주문 상품 조회에 실패하여 주문에 실패하였습니다.");
        }

        // 주문수량만큼 재고가 남아있는지 체크, 재고가 부족하다면 익셉션
        Map<Long, Integer> orderQuantityMap = request.getOrderProducts().stream()
                .collect(Collectors.toMap(op -> op.getProductOptionId(), op -> op.getQuantity()));

        // 재고 체크, 가격 계산
        AtomicLong totalPrice = new AtomicLong();

        System.out.println("======= >> 주문 금액 계산 과정 시작 << ==========");
        productPrices.forEach(p -> {
            if (p.getStockQuantity() < orderQuantityMap.get(p.getProductOptionId())) {
                throw new BusinessException(NOT_ENOUGH_STOCK, "재고가 부족합니다.");
            }
            System.out.println(p.getProductId() + "번 상품 가격 : " + p.getPrice() + "원");
            System.out.println("옵션가격 추가 : " + p.getAdditionalPrice() + "원");
            System.out.println("주문수량 : " + orderQuantityMap.get(p.getProductOptionId()) + "개");
            int orderPrice = (p.getPrice() + p.getAdditionalPrice()) * orderQuantityMap.get(p.getProductOptionId());
            System.out.println("계산결과 = " + orderPrice);
            totalPrice.addAndGet(orderPrice);
            System.out.println("총 금액에 누적하여 총 금액 = " + totalPrice.get());
        });
        System.out.println("======= >> 주문 금액 계산 과정 끝 << ==========");

        // 최종 결제할 금액 계산
        Long payAmount = (totalPrice.get() - request.getUsePoint()) * (couponDiscountRate/100);

        // 주문정보 저장
        Long orderId = orderRepository.save(request.toEntity(totalPrice.get(), payAmount)).getId();

        // 주문 생성 이벤트 메세지 만들기
        List<OrderCreate.OrderQuantity> orderQuantities = request.getOrderProducts().stream()
                .map(op -> new OrderCreate.OrderQuantity(op.getProductOptionId(), op.getQuantity()))
                .collect(Collectors.toList());

        OrderCreate message = OrderCreate.builder()
                .orderId(orderId)
                .memberId(request.getMemberId())
                .couponId(request.getCouponId())
                .usePoint(request.getUsePoint())
                .payAmount(payAmount)
                .orderQuantities(orderQuantities)
                .build();

        log.info("주문요청이 성공적으로 처리되었습니다.");

        // 주문 저장 이벤트 메세지 발행
//        kafkaProducer.send("order-create", message);

        // TODO : 포인트 사용금액만큼 사용 (포인트 시스템에서 구독)
        // TODO : 상품들 재고 감소 (상품 시스템에서 구독)
    }


    // 주문 상세조회
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findByIdWithOrderProducts(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));

        // TODO: OrderResponse 작성
        return OrderResponse.from(order);
    }

}
