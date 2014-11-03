package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.JpaContextConfig;
import io.noah.jpasandbox.ProductDao;
import io.noah.jpasandbox.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class EntityJoingingTests extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext(unitName = "pu-sandbox")
    EntityManager em;

    @Autowired
    @Qualifier("hqlDao")
    ProductDao hqlDao;

    @Autowired
    @Qualifier("criteriaDao")
    ProductDao criteriaDao;

    private void createTestProduct(int count) {
        Date open = toDate("2014-01-01 09:00:00");
        Date end = toDate("2014-12-31 11:59:59");

        for (int i = 1; i <= count; i++) {
            em.persist(new Product("맥북v" + i, "노트북", 1000 * (i + 1), open, end));
        }
    }

    private Date toDate(String source) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
