package com.justshop.delivery.domain.repository;

import com.justshop.delivery.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
