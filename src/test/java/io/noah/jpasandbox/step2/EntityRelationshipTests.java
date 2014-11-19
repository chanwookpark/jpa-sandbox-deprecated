package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.ProductDao;
import io.noah.jpasandbox.ProductTestSupport;
import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by chanwook on 2014. 10. 1..
 */
public class EntityRelationshipTests extends ProductTestSupport {

    @Autowired
    @Qualifier("hqlDao")
    private ProductDao dao;

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

    @Test
    public void oneToManyMany() throws Exception {
        List<Product> resultList = dao.findProductWithItem(2);

        assertNotNull(resultList);
        int resultListSize = resultList.size();
        assertTrue("product size: " + resultListSize, resultListSize == 1);

//        int size = resultList.get(0).getItemList().size();
//        assertTrue("item collection size = " + size, size == 99);
        assertNotNull(resultList.get(0).getItemList().get(0));
        assertNotNull(resultList.get(0).getItemList().get(1));
        assertNotNull(resultList.get(0).getItemList().get(2));

        List<ProductItem> itemList = dao.findProductItem(2);
        assertNotNull(itemList);
        assertTrue("item size: " + itemList.size(), itemList.size() == 99);
    }
}
