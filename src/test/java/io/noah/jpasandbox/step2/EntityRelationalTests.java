package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.ProductTestSupport;
import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by chanwook on 2014. 12. 1..
 */
public class EntityRelationalTests extends ProductTestSupport {

    @Test
    public void oneToMany() throws Exception {
        Product p = em.find(Product.class, 1L);

        loggingAndSleep(1, 500);

        assertNotNull(p);

        assertNotNull(p.getItemList());

        loggingAndSleep(2, 500);

        assertTrue(4 == p.getItemList().size());

        loggingAndSleep(3, 500);
    }

    @Test
    public void findMultiItem() throws Exception {
        TypedQuery<ProductItem> query = em.createQuery(
                "SELECT pi FROM ProductItem pi WHERE pi.product.id = :prdId",
                ProductItem.class);
        query.setParameter("prdId", 1L);
        List<ProductItem> itemList = query.getResultList();

        assertNotNull(itemList);
        assertTrue(4 == itemList.size());
    }
}
