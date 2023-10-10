package com.justshop.product.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.product.DataFactoryUtil;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.justshop.core.error.ErrorCode.PRODUCT_LIKE_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    
    @DisplayName("상품 ID를 받아서 상품 상세조회를 할 수 있다.")
    @Test
    void getProductInfo_success() {
        // given
        Product product = productRepository.save(DataFactoryUtil.generateProduct());
        Long productId = product.getId();

        // when
        ProductResponse productInfo = productService.getProductInfo(productId);

        // then
    }

    @DisplayName("없는 상품 ID라면 BusinessExcetion이 발생한다.")
    @Test
    void getProductInfo_fail() {
        assertThatThrownBy(() -> productService.getProductInfo(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_LIKE_NOT_FOUND.getMessage());
    }

}