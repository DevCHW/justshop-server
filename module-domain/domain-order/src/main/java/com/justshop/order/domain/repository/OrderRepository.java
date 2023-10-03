package com.justshop.order.domain.repository;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.repository.query.OrderQueryRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {

    @EntityGraph(attributePaths = {"orderProducts"})
    Optional<Order> findByIdWithOrderProducts(@Param("id") Long id);
}
