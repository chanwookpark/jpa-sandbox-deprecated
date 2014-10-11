package io.noah.jpasandbox.commerce;

import io.noah.jpasandbox.commerce.repository.ProductRepository;
import io.noah.jpasandbox.config.JpaContextConfig;
import io.noah.jpasandbox.framework.Criteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 10. 6..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class BasicCrudTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ProductRepository prdRepo;

    @Test
    public void simpleProductCrud() throws Exception {

        Product p = new Product("뉴팩토리");
        Criteria c = Criteria.create().eq("manufacturer", p.getManufacturer());

        // 처음 조회 시에는 없고..
        Product notFoundProduct = prdRepo.findByCriteria(c).get(0);
        assertNull(notFoundProduct);

        // 새로 생성하고
        Product persistentProduct = prdRepo.save(p);
        assertNotNull(persistentProduct);
        assertEquals(p.getManufacturer(), persistentProduct.getManufacturer());

        // 조회하면 있고 (1st cache 지만..) -> SQL이 실행 안 되는지를 확인하자
        Product foundProduct = prdRepo.findByCriteria(c).get(0);
        assertNotNull(foundProduct);
        assertEquals(p.getManufacturer(), foundProduct.getManufacturer());

        // 수정하고 (-> SQL 수행 확인)
        foundProduct.setManufacturer("뉴뉴팩토리");
        Product updatedProduct = prdRepo.update(foundProduct);

        assertNotNull(updatedProduct);
        assertEquals(foundProduct.getManufacturer(), updatedProduct.getManufacturer());

        // 다시 조회하고
        c.eq("manufacturer", foundProduct.getManufacturer());
        foundProduct = prdRepo.findByCriteria(c).get(0);
        assertEquals(c.get("manufacturer"), foundProduct.getManufacturer());

        // 삭제하고 (-> SQL 수행 확인)
        boolean result = prdRepo.remove(updatedProduct);
        assertTrue(result);

        // 다시 조회하면 없고..
        assertTrue(0 == prdRepo.findByCriteria(c).size());

    }
}
