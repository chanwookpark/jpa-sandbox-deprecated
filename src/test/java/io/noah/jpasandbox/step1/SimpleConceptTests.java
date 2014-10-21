package io.noah.jpasandbox.step1;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class SimpleConceptTests extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext(unitName = "pu-sandbox")
    EntityManager em;

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
        assertEquals(p.getSaleEnd(), loaded.getSaleEnd());

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
}
