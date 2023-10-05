package com.justshop.point.domain.repository;

import com.justshop.point.domain.entity.PointEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointEventHistoryRepository extends JpaRepository<PointEventHistory, Long> {
}
