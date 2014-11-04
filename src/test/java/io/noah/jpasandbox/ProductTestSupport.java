package io.noah.jpasandbox;

import io.noah.jpasandbox.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static io.noah.jpasandbox.util.DateUtil.toDate;

/**
 * Created by chanwook on 2014. 11. 4..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class ProductTestSupport extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext(unitName = "pu-sandbox")
    protected EntityManager em;

    @Autowired
    @Qualifier("hqlDao")
    protected ProductDao hqlDao;

    @Autowired
    @Qualifier("criteriaDao")
    protected ProductDao criteriaDao;

    protected void createTestProduct(int count) {
        Date open = toDate("2014-01-01 09:00:00");
        Date end = toDate("2014-12-31 11:59:59");

        for (int i = 1; i <= count; i++) {
            em.persist(new Product("맥북v" + i, "노트북", 1000 * (i + 1), open, end));
        }
    }
}
