package com.justshop.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @Autowired
    protected EntityManager entityManager;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
