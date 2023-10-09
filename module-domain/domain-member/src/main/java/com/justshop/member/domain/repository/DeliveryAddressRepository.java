package com.justshop.member.domain.repository;

import com.justshop.member.domain.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    List<DeliveryAddress> findAllByMemberId(Long memberId);

    Boolean existsByMemberId(Long memberId);
}
