package com.justshop.order.domain.repository;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.repository.query.OrderQueryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {

    @EntityGraph(attributePaths = {"orderProducts"})
    Optional<Order> findWithOrderProductsById(Long id);
}
