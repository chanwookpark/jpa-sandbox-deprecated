package io.noah.jpasandbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class ProductTestDb extends AbstractJUnit4SpringContextTests {

    @Autowired
    PlatformTransactionManager txManager;

    @PersistenceContext(unitName = "pu-sandbox")
    protected EntityManager em;
}
