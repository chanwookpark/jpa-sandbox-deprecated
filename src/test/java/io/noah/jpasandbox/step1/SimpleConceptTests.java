package io.noah.jpasandbox.step1;

import io.noah.jpasandbox.JpaContextConfig;
import io.noah.jpasandbox.ProductDao;
import io.noah.jpasandbox.model.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("hqlDao")
    ProductDao hqlDao;

    @Autowired
    @Qualifier("criteriaDao")
    ProductDao criteriaDao;

    @Test
    public void config() throws Exception {
        assertNotNull(em);
    }

    @Test
    public void simpleCrud() {
        Product p = new Product("맥북", "노트북", 1000, new Date(), new Date());

        // 저장 (C)
        em.persist(p);

        // 조회 (R)
        Product loaded = em.find(Product.class, p.getId());
        assertNotNull(loaded);
        assertEquals(p.getCategory(), loaded.getCategory());
        assertEquals(p.getName(), loaded.getName());
        assertEquals(p.getSalePrice(), loaded.getSalePrice());
        assertEquals(p.getSaleOpen(), loaded.getSaleOpen());
        assertEquals(p.getSaleClose(), loaded.getSaleClose());

        // 수정 (U)
        String newName = "맥북리뉴";
        loaded.setName(newName);

        loaded = em.find(Product.class, p.getId());
        assertEquals(newName, loaded.getName());

        // 삭제 (D)
        em.remove(loaded);

        loaded = em.find(Product.class, p.getId());
        assertNull(loaded);
    }

    @Test
    public void queryWithHql() throws Exception {
        createTestProduct(10);

        List<Product> list = hqlDao.find();
        assertTrue(10 == list.size());

        list = hqlDao.find(2, 3);
        assertTrue("real: " + list.size(), 3 == list.size());
        /* 기본 정렬일 때
        assertEquals("맥북v4", list.get(0).getName());
        assertEquals("맥북v5", list.get(1).getName());
        assertEquals("맥북v6", list.get(2).getName());
        */

        /* 높은 가격 순 정렬 일 때*/
        assertEquals("맥북v7", list.get(0).getName());
        assertEquals("맥북v6", list.get(1).getName());
        assertEquals("맥북v5", list.get(2).getName());

        // 현재 판매 가능 상품만
        em.persist(new Product("옛날맥북", "노트북", 100000, toDate("2013-01-01 09:00:00"), toDate("2013-12-31 11:59:59")));
        em.persist(new Product("미래맥북", "노트북", 1000000, toDate("2015-01-01 09:00:00"), toDate("2015-12-31 11:59:59")));

        list = hqlDao.find(1, 5);
        assertEquals("맥북v10", list.get(0).getName());
        assertEquals("맥북v9", list.get(1).getName());
        assertEquals("맥북v8", list.get(2).getName());
        assertEquals("맥북v7", list.get(3).getName());
        assertEquals("맥북v6", list.get(4).getName());

        em.persist(new Product("애플TV", "가전제품", 10000000, toDate("2014-01-01 09:00:00"), toDate("2014-12-31 11:59:59")));
        list = hqlDao.find(1, 3, "노트북");
        assertTrue(3 == list.size());
        assertEquals("맥북v10", list.get(0).getName());
        assertEquals("맥북v9", list.get(1).getName());
        assertEquals("맥북v8", list.get(2).getName());
    }

    @Test
    public void queryWithCriteria() throws Exception {
        createTestProduct(10);

        List<Product> list = criteriaDao.find();
        assertTrue(10 == list.size());

        list = criteriaDao.find(2, 3);
        assertTrue("real: " + list.size(), 3 == list.size());
        /* 기본 정렬일 때
        assertEquals("맥북v4", list.get(0).getName());
        assertEquals("맥북v5", list.get(1).getName());
        assertEquals("맥북v6", list.get(2).getName());
        */

        /* 높은 가격 순 정렬 일 때*/
        assertEquals("맥북v7", list.get(0).getName());
        assertEquals("맥북v6", list.get(1).getName());
        assertEquals("맥북v5", list.get(2).getName());

        // 현재 판매 가능 상품만
        em.persist(new Product("옛날맥북", "노트북", 100000, toDate("2013-01-01 09:00:00"), toDate("2013-12-31 11:59:59")));
        em.persist(new Product("미래맥북", "노트북", 1000000, toDate("2015-01-01 09:00:00"), toDate("2015-12-31 11:59:59")));

        list = criteriaDao.find(1, 5);
        assertEquals("맥북v10", list.get(0).getName());
        assertEquals("맥북v9", list.get(1).getName());
        assertEquals("맥북v8", list.get(2).getName());
        assertEquals("맥북v7", list.get(3).getName());
        assertEquals("맥북v6", list.get(4).getName());

        em.persist(new Product("애플TV", "가전제품", 10000000, toDate("2014-01-01 09:00:00"), toDate("2014-12-31 11:59:59")));
        list = criteriaDao.find(1, 3, "노트북");
        assertTrue(3 == list.size());
        assertEquals("맥북v10", list.get(0).getName());
        assertEquals("맥북v9", list.get(1).getName());
        assertEquals("맥북v8", list.get(2).getName());
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
