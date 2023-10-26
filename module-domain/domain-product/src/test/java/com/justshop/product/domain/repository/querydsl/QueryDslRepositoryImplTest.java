package com.justshop.product.domain.repository.querydsl;

import com.justshop.product.DataFactoryUtil;
import com.justshop.product.RepositoryTestSupport;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.QProduct;
import com.justshop.product.domain.repository.ProductRepository;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QueryDslRepositoryImplTest extends RepositoryTestSupport {

    @Autowired
    private QueryDslRepositoryImpl queryDslRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품ID를 받아서 상품 상세조회를 한다.")
    @Test
    void findProductDto_success() {
        // given
        Product product = productRepository.save(DataFactoryUtil.generateProduct());
        Long productId = product.getId();

        // when
        ProductDto result = productRepository.findProductDto(productId);
        // then
        Assertions.assertThat(result.getProductId()).isEqualTo(product.getId());
        Assertions.assertThat(result.getName()).isEqualTo(product.getName());
        Assertions.assertThat(result.getPrice()).isEqualTo(product.getPrice());
        Assertions.assertThat(result.getSalesQuantity()).isEqualTo(product.getSalesQuantity());
        Assertions.assertThat(result.getLikeCount()).isEqualTo(product.getLikeCount());
        Assertions.assertThat(result.getReviewCount()).isEqualTo(product.getReviewCount());
        Assertions.assertThat(result.getStatus()).isEqualTo(product.getStatus());
        Assertions.assertThat(result.getGender()).isEqualTo(product.getGender());
        Assertions.assertThat(result.getDetail()).isEqualTo(product.getProductDetail().getDescription());
        Assertions.assertThat(result.getProductOptions().size()).isEqualTo(product.getProductOptions().size());
        Assertions.assertThat(result.getImages().size()).isEqualTo(product.getProductImages().size());
    }


}