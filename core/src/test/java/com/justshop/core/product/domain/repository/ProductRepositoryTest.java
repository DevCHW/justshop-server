package com.justshop.core.product.domain.repository;

import com.justshop.core.product.domain.Product;
import com.justshop.core.product.domain.enums.ProductGender;
import com.justshop.core.product.domain.enums.ProductSellingStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품번호 목록으로 상품목록을 조회한다.")
    @Test
    void findAllByCodeIn() {
        // given
        Product product1 = generateProduct("targetProduct1");
        Product product2 = generateProduct("targetProduct2");
        Product product3 = generateProduct("testProduct3");
        Product product4 = generateProduct("testProduct4");
        Product product5 = generateProduct("testProduct5");

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));

        List<Product> targets = List.of(product1, product2);

        List<String> productCodes = targets.stream()
                .map(Product::getCode)
                .collect(Collectors.toList());

        // when
        List<Product> result = productRepository.findAllByCodeIn(productCodes);

        // then
        Assertions.assertThat(result).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "targetProduct1",
                        "targetProduct2"
                );
    }

    private Product generateProduct(String name) {
        return Product.builder()
                .name(name)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(1000)
                .brandName("TestBrand")
                .gender(ProductGender.ALL)
                .description("testProduct")
                .build();
    }

}