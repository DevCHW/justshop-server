package com.justshop.core.product.domain.repository;

import com.justshop.core.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCodeIn(List<String> codes);

    List<Product> findAllByIdIn(Set<Long> ids);
}
