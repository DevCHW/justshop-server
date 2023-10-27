package com.justshop.product;

import com.justshop.jpa.JpaConfig;
import com.justshop.jpa.config.QueryDslConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({JpaConfig.class, QueryDslConfig.class})
public abstract class RepositoryTestSupport {
}
