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
import java.util.UUID;

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
            product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지1.PNG", "C://file/product/images", true);
            product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지2.PNG", "C://file/product/images", false);
            product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지3.PNG", "C://file/product/images", false);
            product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지4.PNG", "C://file/product/images", false);
            product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지5.PNG", "C://file/product/images", false);
            product.addProductDetail("Test Product Description .");

            em.persist(product);
        }
    }
}
