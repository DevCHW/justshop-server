package com.justshop.product;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initializeProduct();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {
        private final EntityManager em;

        public void initializeProduct() {
            Product product =  Product.builder()
                    .name("Test Product")
                    .price(10000)
                    .salesQuantity(10000L)
                    .likeCount(10000L)
                    .reviewCount(10000L)
                    .status(SellingStatus.SELLING)
                    .gender(Gender.FREE)
                    .build();

            product.addProductCategory(1L);
            product.addProductCategory(2L);
            product.addProductCategory(3L);
            product.addProductOption(Size.S, Color.BLACK, null, 0, 100);
            product.addProductOption(Size.M, Color.BLACK, null, 0, 200);
            product.addProductOption(Size.L, Color.BLACK, null, 0, 300);
            product.addProductOption(Size.S, Color.WHITE, null, 0, 300);
            product.addProductOption(Size.M, Color.WHITE, null, 0, 400);
            product.addProductOption(Size.L, Color.WHITE, null, 0, 500);
            product.addProductImage(1L, true);
            product.addProductImage(2L, false);
            product.addProductImage(3L, false);
            product.addProductImage(4L, false);
            product.addProductImage(5L, false);
            product.addProductDetail("Test Product Description .");

            em.persist(product);
        }
    }
}
