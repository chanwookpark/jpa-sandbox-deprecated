package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.ProductTestSupport;
import io.noah.jpasandbox.model.Product;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by chanwook on 2014. 10. 1..
 */
public class EntityRelationshipTests extends ProductTestSupport {

    @Test
    public void oneToMany() throws Exception {
        Product p = em.find(Product.class, 1L);

        loggingAndSleep(1, 500);

        // product.itemList == wrapper인 시점 로딩
        assertNotNull(p);

        loggingAndSleep(2, 500);

        // 이 때 product.itemList 로딩
        assertNotNull(p.getItemList());
        assertTrue(4 == p.getItemList().size());

        loggingAndSleep(3, 0);

    }
}
