package io.noah.jpasandbox;

import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static io.noah.jpasandbox.util.DateUtil.toDate;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class ProductTestDb extends AbstractJUnit4SpringContextTests {

    @Autowired
    PlatformTransactionManager txManager;

    @PersistenceContext(unitName = "pu-sandbox")
    protected EntityManager em;

    @Test
    public void createOneToMany() throws Exception {
        new TransactionTemplate(txManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                Product p = new Product("맥북에어", "노트북", 1000, toDate("2014-01-01 09:00:00"), toDate("2014-12-30 11:59:59"));
                ProductItem item1 = new ProductItem("11인치 기본형", 0, 100);
                ProductItem item2 = new ProductItem("11인치 고급형", 100, 100);
                ProductItem item3 = new ProductItem("13인치 기본형", 500, 100);
                ProductItem item4 = new ProductItem("13인치 고급형", 1000, 100);
                p.addItem(item1);
                p.addItem(item2);
                p.addItem(item3);
                p.addItem(item4);

//                em.persist(item1);
//                em.persist(item2);
//                em.persist(item3);
//                em.persist(item4);

                em.persist(p);

            }
        });
    }

}
