package com.justshop.core.order.command;

import com.justshop.core.order.domain.Order;
import com.justshop.core.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderManager {

    private final OrderRepository orderRepository;

    public Long create(Order order) {
        return orderRepository.save(order).getId();
    }

}
