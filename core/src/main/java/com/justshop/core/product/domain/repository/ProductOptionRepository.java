package com.justshop.core.product.domain.repository;

import com.justshop.core.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProductIdInAndOptionIdIn(List<Long> productIds, List<Long> optionIds);

}
