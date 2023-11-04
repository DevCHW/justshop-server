package com.justshop.product.domain.repository;

import com.justshop.product.RepositoryTestSupport;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class ProductOptionRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @DisplayName("상품 옵션 ID 리스트를 받아 해당하는 ID의 상품 옵션을 반환한다.")
    @Test
    void findAllByIdIn_success() {
        // given
        List<ProductOption> productOptionList = new ArrayList<>();

        for(int i=0; i<5; i++) {
            productOptionList.add(generateProduct());
        }
        List<ProductOption> productOptions = productOptionRepository.saveAll(productOptionList);
        List<Long> optionIds = productOptions.stream().map(productOption -> productOption.getId())
                .collect(Collectors.toList());

        // when
        List<ProductOption> result = productOptionRepository.findAllByIdIn(optionIds);
        List<Long> findIds = result.stream().map(productOption -> productOption.getId())
                .collect(Collectors.toList());

        // then
        assertThat(result).hasSize(5);
        assertThat(findIds).hasSize(5).isEqualTo(optionIds);
    }

    private ProductOption generateProduct() {
        return ProductOption.builder()
                .productSize(Size.M)
                .color(Color.BLACK)
                .etc(null)
                .additionalPrice(0)
                .stockQuantity(100)
                .build();
    }

}