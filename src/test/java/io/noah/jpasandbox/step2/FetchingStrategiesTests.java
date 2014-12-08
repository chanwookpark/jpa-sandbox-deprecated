package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.ProductTestSupport;
import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 12. 1..
 */
public class FetchingStrategiesTests extends ProductTestSupport {
    @Test
    public void getReference() throws Exception {
        loggingAndSleep(1);

        Product reference = em.getReference(Product.class, 1L);
        assertNotNull(reference);

        loggingAndSleep(2);

        assertEquals(1L, reference.getId());

        loggingAndSleep(3);

        assertEquals("노트북", reference.getCategory());

        loggingAndSleep(4);

        // 언제쓰나?
        ProductItem item = new ProductItem("신규 모델", 100, 10);
        reference.addItem(item); // reference 추가 용으로만 Product 사용

        em.persist(reference);

        // assertion
        Integer count = em.createQuery(
                "SELECT size(p.itemList) FROM Product p WHERE p.id = 1", Integer.class)
                .getSingleResult();

        assertTrue("size: " + count, count == 5);
    }

    @Test
    public void fetching() throws Exception {
        List<Product> productList =
                em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        assertNotNull(productList);

        for(Product p : productList) {
            assertNotNull(p);
            assertNotNull(p.getItemList().get(0).getItemName());
            assertNotNull(p.getItemList().get(1).getItemName());
            assertNotNull(p.getItemList().get(2).getItemName());
            assertNotNull(p.getItemList().get(3).getItemName());
        }
    }
}
