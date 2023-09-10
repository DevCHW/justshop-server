package com.justshop.core.order.domain.repository;

import com.justshop.core.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
