package com.justshop.core.product.implementation.query;

import com.justshop.core.common.ErrorCode;
import com.justshop.core.common.exception.EntityNotFoundException;
import com.justshop.core.product.domain.Product;
import com.justshop.core.product.persistence.jpa.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductReader {

    private final ProductRepository productRepository;

    // 판매중인 상품목록인지 체크
    public void checkSellingStatus(Set<Long> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);

        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND, "Not Found");
        }
        products.stream()
                .forEach(Product::checkSellingStatus);
    }

}
