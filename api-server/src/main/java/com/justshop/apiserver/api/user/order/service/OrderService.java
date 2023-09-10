package com.justshop.apiserver.api.user.order.service;

import com.justshop.apiserver.api.user.order.service.dto.CreateOrderResponse;
import com.justshop.apiserver.api.user.order.service.dto.CreateOrderServiceRequest;
import com.justshop.core.member.command.PointManager;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.query.MemberReader;
import com.justshop.core.order.command.OrderManager;
import com.justshop.core.payment.command.PaymentManager;
import com.justshop.core.product.command.StockManager;
import com.justshop.core.product.query.ProductReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.justshop.apiserver.api.user.order.controller.dto.request.CreateOrderRequest.*;
import static com.justshop.core.member.command.PointManager.*;
import static com.justshop.core.member.command.PointManager.USE_POINT_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberReader memberReader;
    private final ProductReader productReader;
    private final StockManager stockManager;
    private final PointManager pointManager;
    private final PaymentManager paymentManager;
    private final OrderManager orderManager;

    // 주문 비즈니스로직 TODO: 동시성이슈 해결하기(Optimistic Lock, Pessimistic Lock 등..)
    @Transactional
    public CreateOrderResponse businessOrder(CreateOrderServiceRequest request) {
        // 주문한 상품목록이 현재 판매중인 상품들인지 체크
        productReader.checkSellingStatus(extractProductIdSet(request));

        // 상품들 재고 감소
        deductStockQuantity(request);

        // 주문자 정보 조회
        Member consumer = memberReader.read(request.getMemberId());

        // 포인트 사용금액만큼 사용
        pointManager.use(consumer, request.getUsePoint(), USE_POINT_MESSAGE);

        // 결제금액 계산, 결제 히스토리 남기기
        int payAmount = calculatePayAmount(request);
        paymentManager.append(payAmount, consumer, request.getGroup(), request.getType());

        // 결제금액의 3% 포인트 적립
        pointManager.append(consumer, calculatePoint(payAmount), APPEND_POINT_MESSAGE);

        // 주문정보 저장
        Long savedId = orderManager.create(request.toEntity());
        return CreateOrderResponse.from(savedId);
    }

    private int calculatePayAmount(CreateOrderServiceRequest request) {
        return request.getTotalPrice() - request.getDiscountAmount() - request.getUsePoint();
    }

    private int calculatePoint(int payAmount) {
        return (int) (payAmount * 0.03);
    }

    private void deductStockQuantity(CreateOrderServiceRequest request) {
        List<Long> productIds = extractProductIdList(request);
        List<Long> optionIds = extractOptionIdList(request);
        stockManager.deductQuantity(productIds, optionIds);
    }

    private List<Long> extractProductIdList(CreateOrderServiceRequest request) {
        return request.getProductOptions().stream()
                .map(ProductOptionRequest::getProductId)
                .collect(Collectors.toList());
    }

    private List<Long> extractOptionIdList(CreateOrderServiceRequest request) {
        return request.getProductOptions().stream()
                .map(ProductOptionRequest::getOptionId)
                .collect(Collectors.toList());
    }

    private Set<Long> extractProductIdSet(CreateOrderServiceRequest request) {
        return request.getProductOptions().stream()
                .map(ProductOptionRequest::getProductId)
                .collect(Collectors.toSet());
    }

    // TODO : 주문 취소
    public void cancel(Long id) {

    }
    
    // TODO : 주문내역 조회 (페이징)
}
