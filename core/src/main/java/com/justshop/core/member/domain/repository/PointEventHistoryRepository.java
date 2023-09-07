package com.justshop.core.member.domain.repository;

import com.justshop.core.member.domain.PointEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointEventHistoryRepository extends JpaRepository<PointEventHistory, Long> {
}
