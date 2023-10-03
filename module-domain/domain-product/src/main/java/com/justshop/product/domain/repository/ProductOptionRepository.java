package com.justshop.product.domain.repository;

import com.justshop.product.domain.entity.ProductOption;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @EntityGraph(attributePaths = {"product"})
    List<ProductOption> findAllByIdIn(List<Long> ids);
}
