package com.justshop.order.infrastructure.kafka.consume.service;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.order.OrderFailReason;
import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderConsumerService {

    private final OrderRepository orderRepository;

    /* 주문 실패 */
    public void fail(Long id, OrderFailReason reason) {
        log.info("ID {} Order Fail. Reason={}", id, reason.getDescription());

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        order.fail();
    }

    /* 주문 성공 */
    public void success(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        order.success();
    }

}

