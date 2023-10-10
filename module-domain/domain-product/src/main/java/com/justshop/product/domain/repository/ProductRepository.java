package com.justshop.product.domain.repository;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.repository.querydsl.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslRepository {

}
