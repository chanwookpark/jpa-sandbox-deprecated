package io.noah.jpasandbox.step1;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 10. 23..
 */
@ContextConfiguration(classes = JpaContextConfig.class)
public class SimpleConceptTests
        extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext(name = "pu-sandbox")
    EntityManager em;

    @Test
    public void checkConfig() throws Exception {
        assertNotNull(em);
    }

    @Test
    public void simpleCrud() throws Exception {
        Product p = new Product("맥북", "노트북", 100L, new Date(), new Date());

        // 저장(C)
        em.persist(p);

        // 조회(R)
        Product loaded = em.find(Product.class, p.getId());

        assertNotNull(loaded);
        assertEquals(p.getName(), loaded.getName());
        assertEquals(p.getCategory(), loaded.getCategory());

        // 수정(U)
        loaded.setName("뉴맥북");

        Product newLoad = em.find(Product.class, p.getId());
        assertEquals("뉴맥북", newLoad.getName());

        // 삭제(D)
        em.remove(newLoad);

        assertNull(em.find(Product.class, p.getId()));
    }
}
