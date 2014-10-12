package io.noah.jpasandbox.commerce;

import io.noah.jpasandbox.commerce.product.model.Product;
import io.noah.jpasandbox.commerce.product.repository.ProductRepository;
import io.noah.jpasandbox.config.JpaContextConfig;
import io.noah.jpasandbox.framework.Criteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by chanwook on 2014. 10. 6..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class ProductBasicTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ProductRepository prdRepo;

    @Test
    public void simpleProductCrud() throws Exception {

        Product p = createProduct();
        Criteria c = Criteria.create().eq("manufacturer", p.getManufacturer());

        // 처음 조회 시에는 없고..
        assertTrue(0 == prdRepo.findByCriteria(c).size());

        // 새로 생성하고
        Product persistentProduct = prdRepo.save(p);
        assertNotNull(persistentProduct);
        assertEquals(p.getManufacturer(), persistentProduct.getManufacturer());

        // 조회하면 있고 -> SQL이 실행 안 되는지를 확인하자 -> 실행 됨
        Product foundProduct = prdRepo.findByCriteria(c).get(0);
        assertNotNull(foundProduct);
        assertEquals(p.getManufacturer(), foundProduct.getManufacturer());

        // 수정하고 (-> SQL 수행 확인)
        foundProduct.setManufacturer("뉴 애플닷컴");
        Product updatedProduct = prdRepo.update(foundProduct);

        assertNotNull(updatedProduct);
        assertEquals(foundProduct.getManufacturer(), updatedProduct.getManufacturer());

        // 다시 조회하고
        c.eq("manufacturer", foundProduct.getManufacturer());
        foundProduct = prdRepo.findByCriteria(c).get(0);
        assertEquals(c.get("manufacturer"), foundProduct.getManufacturer());

        // 삭제하고 (-> SQL 수행 확인)
        prdRepo.remove(updatedProduct);

        // 다시 조회하면 없고..
        assertTrue(0 == prdRepo.findByCriteria(c).size());

    }

    private Product createProduct() {
        return new Product("Apple Inc.", 100L, "맥북 2014 late", "최고급 맥북 14년 late 버전", new Date(), new Date());
    }
}
