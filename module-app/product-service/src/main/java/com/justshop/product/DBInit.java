package com.justshop.product;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductImage;
import com.justshop.product.domain.entity.ProductOption;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInit {

    private final InitService initService;

//    @PostConstruct
    public void init() {
        initService.initializeProduct();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {
        private final EntityManager em;

        public void initializeProduct() {

            for(long i=0; i<100; i++) {
                Product product =  Product.builder()
                        .name("Test_Product" + i)
                        .price((int) (10000 + i))
                        .salesQuantity(i)
                        .likeCount(i)
                        .reviewCount(i)
                        .status(SellingStatus.SELLING)
                        .gender(Gender.FREE)
                        .build();

                product.addProductCategory(1L);
                product.addProductCategory(2L);
                product.addProductCategory(3L);

                ProductOption productOption1 = new ProductOption(product, Size.S, Color.BLACK, null, 0, 100);
                ProductOption productOption2 = new ProductOption(product, Size.M, Color.BLACK, null, 0, 200);
                ProductOption productOption3 = new ProductOption(product, Size.L, Color.BLACK, null, 0, 300);
                ProductOption productOption4 = new ProductOption(product, Size.S, Color.WHITE, null, 0, 300);
                ProductOption productOption5 = new ProductOption(product, Size.M, Color.WHITE, null, 0, 400);
                ProductOption productOption6 = new ProductOption(product, Size.L, Color.WHITE, null, 0, 500);
                product.addAllProductOption(List.of(productOption1, productOption2, productOption3, productOption4, productOption5, productOption6));

                ProductImage productImage1 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지1.PNG", "C://file/product/images", true);
                ProductImage productImage2 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지2.PNG", "C://file/product/images", false);
                ProductImage productImage3 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지3.PNG", "C://file/product/images", false);
                ProductImage productImage4 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지4.PNG", "C://file/product/images", false);
                ProductImage productImage5 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지5.PNG", "C://file/product/images", false);
                product.addAllProductImage(List.of(productImage1, productImage2, productImage3, productImage4, productImage5));
                product.addProductDetail(product.getName() + "의 Description.");

                em.persist(product);
            }

            for(int i=0; i<100; i++) {
                Product product =  Product.builder()
                        .name("2번 카테고리 Test_Product" + i)
                        .price(10000 + i)
                        .salesQuantity(10000L)
                        .likeCount(10000L)
                        .reviewCount(10000L)
                        .status(SellingStatus.SELLING)
                        .gender(Gender.FREE)
                        .build();

                product.addProductCategory(2L);

                ProductOption productOption1 = new ProductOption(product, Size.S, Color.BLACK, null, 0, 100);
                ProductOption productOption2 = new ProductOption(product, Size.M, Color.BLACK, null, 0, 200);
                ProductOption productOption3 = new ProductOption(product, Size.L, Color.BLACK, null, 0, 300);
                ProductOption productOption4 = new ProductOption(product, Size.S, Color.WHITE, null, 0, 300);
                ProductOption productOption5 = new ProductOption(product, Size.M, Color.WHITE, null, 0, 400);
                ProductOption productOption6 = new ProductOption(product, Size.L, Color.WHITE, null, 0, 500);
                product.addAllProductOption(List.of(productOption1, productOption2, productOption3, productOption4, productOption5, productOption6));

                ProductImage productImage1 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지1.PNG", "C://file/product/images", true);
                ProductImage productImage2 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지2.PNG", "C://file/product/images", false);
                ProductImage productImage3 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지3.PNG", "C://file/product/images", false);
                ProductImage productImage4 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지4.PNG", "C://file/product/images", false);
                ProductImage productImage5 = new ProductImage(product, UUID.randomUUID()+".PNG", product.getName() + "의 상품이미지5.PNG", "C://file/product/images", false);
                product.addAllProductImage(List.of(productImage1, productImage2, productImage3, productImage4, productImage5));
                product.addProductDetail(product.getName() + "의 Description.");

                em.persist(product);
            }
        }
    }
}
