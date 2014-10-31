package io.noah.jpasandbox.step1;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class SimpleConceptTests extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext(unitName = "pu-sandbox")
    EntityManager em;

    @Autowired
    ProductDao hqlDao;

    @Test
    public void config() throws Exception {
        assertNotNull(em);
    }

    @Test
    public void queryWithHql() throws Exception {
        createTestProduct(10);

        List<Product> list = hqlDao.find();
        assertTrue(10 == list.size());

        list = hqlDao.find(3, 2);

        assertTrue(3 == list.size());
        /* 정렬조건 안 줬을때.. */
//        assertEquals(4, list.get(0).getId());
//        assertEquals(5, list.get(1).getId());
//        assertEquals(6, list.get(2).getId());

        assertEquals(7, list.get(0).getId());
        assertEquals(6, list.get(1).getId());
        assertEquals(5, list.get(2).getId());

        // 판매가능상품인지 비교하자.. (11개)
        em.persist(
                new Product("old맥북", "노트북", 1000000,
                        toDate("2013-01-01 12:00:00"), toDate("2013-12-30 12:00:00")));

        list = hqlDao.find(3, 2);
        assertTrue(3 == list.size());
        assertEquals(7, list.get(0).getId());
        assertEquals(6, list.get(1).getId());
        assertEquals(5, list.get(2).getId());

        // 카테고리 비교 (12개)
        em.persist(
                new Product("32인치TV", "TV", 1000000,
                        toDate("2014-01-01 12:00:00"), toDate("2014-12-30 12:00:00")));

        list = hqlDao.find(3, 2, "노트북");
        assertTrue(3 == list.size());
        assertEquals(7, list.get(0).getId());
        assertEquals(6, list.get(1).getId());
        assertEquals(5, list.get(2).getId());

    }

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
