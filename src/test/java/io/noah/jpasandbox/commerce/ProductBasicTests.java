package io.noah.jpasandbox.commerce;

import io.noah.jpasandbox.commerce.product.model.Product;
import io.noah.jpasandbox.commerce.product.model.ProductStatus;
import io.noah.jpasandbox.commerce.product.repository.ProductRepository;
import io.noah.jpasandbox.config.JpaContextConfig;
import io.noah.jpasandbox.framework.Criteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;
import java.util.List;

import static io.noah.jpasandbox.framework.utils.DateUtils.getDate;
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

        Product p = createProduct(getDate(2014, 1, 1), getDate(2014, 12, 30), "뉴애플1");
        Criteria c = Criteria.create().eq("manufacturer", p.getManufacturer());

        // 처음 조회 시에는 없고..
        assertTrue(0 == prdRepo.findByHql(c).size());

        // 새로 생성하고
        Product persistentProduct = prdRepo.save(p);
        assertNotNull(persistentProduct);
        assertEquals(p.getManufacturer(), persistentProduct.getManufacturer());

        // 조회하면 있고 -> SQL이 실행 안 되는지를 확인하자 -> 실행 됨
        Product foundProduct = prdRepo.findByHql(c).get(0);
        assertNotNull(foundProduct);
        assertEquals(p.getManufacturer(), foundProduct.getManufacturer());

        // 수정하고 (-> SQL 수행 확인)
        foundProduct.setManufacturer("뉴 애플2");
        Product updatedProduct = prdRepo.update(foundProduct);

        assertNotNull(updatedProduct);
        assertEquals(foundProduct.getManufacturer(), updatedProduct.getManufacturer());

        // 다시 조회하고
        c.eq("manufacturer", foundProduct.getManufacturer());
        foundProduct = prdRepo.findByHql(c).get(0);
        assertEquals(c.get("manufacturer"), foundProduct.getManufacturer());

        // 삭제하고 (-> SQL 수행 확인)
        prdRepo.remove(updatedProduct);

        // 다시 조회하면 없고..
        assertTrue(0 == prdRepo.findByHql(c).size());

    }

    @Test
    public void listingProductWithHql() throws Exception {
        String manufacturer = "Apple Inc.";

        // 일단 모든 상품을 한 번 가져와보자.
        List<Product> all = prdRepo.findAllByHql();
        assertTrue("current = " + all.size(), 5 == all.size());

        // 페이징을 해보자
        Criteria c1 = Criteria.create().eq("manufacturer", manufacturer).page(2).item(2);
        List<Product> paged = prdRepo.findByHql(c1);
        assertTrue("current = " + paged.size(), 2 == paged.size());

        // order by를 추가해서 정확히 원하는대로 왔는지 확인하자 (동적으로 할 수 있으나 여기서는 SQL을 직접 수정해서 해본다!)
        Criteria c2 = Criteria.create().eq("manufacturer", manufacturer).page(2).item(2);
        List<Product> orderedPage = prdRepo.findByHql(c2);
        assertTrue("current = " + orderedPage.size(), 2 == orderedPage.size());
        assertEquals(new Long(80), orderedPage.get(0).getSalePrice());
        assertEquals(new Long(70), orderedPage.get(1).getSalePrice());

        /**
         * 파라미터 필터링
         * 1. 판매가능일시 체크
         * 2. 상품 상태 체크 (= OP)
         */
        prdRepo.save(createProduct(getDate(2014, 1, 1), getDate(2014, 1, 2), manufacturer));
        Product np = createProduct(getDate(2014, 1, 1), getDate(2014, 12, 31), manufacturer);
        np.setStatus(ProductStatus.SO); // 일시품절 상태
        prdRepo.save(np);
        assertTrue(7 == prdRepo.findAllByHql().size()); // 추가됐는지 확인

        // 필터링 됐는지 체크
        Criteria c3 = Criteria.create().eq("manufacturer", manufacturer).page(1).item(10);
        assertTrue("current = " + prdRepo.findByHql(c3).size(), 5 == prdRepo.findByHql(c3).size());

        List<Product> filteredList = prdRepo.findByHql(c2);
        assertTrue(2 == filteredList.size());
        assertEquals(new Long(80), filteredList.get(0).getSalePrice());
        assertEquals(new Long(70), filteredList.get(1).getSalePrice());
    }

    @Test
    public void listingProductWithCriteriaBuilder() throws Exception {
        String manufacturer = "Apple Inc.";

        // 일단 모든 상품을 한 번 가져와보자.
        List<Product> all = prdRepo.findAllByCriteria();
        assertTrue("current = " + all.size(), 5 == all.size());

        // 페이징을 해보자
        Criteria c1 = Criteria.create().eq("manufacturer", manufacturer).page(2).item(2);
        List<Product> paged = prdRepo.findByCriteria(c1);
        assertTrue("current = " + paged.size(), 2 == paged.size());

        // order by를 추가해서 정확히 원하는대로 왔는지 확인하자 (동적으로 할 수 있으나 여기서는 SQL을 직접 수정해서 해본다!)
        Criteria c2 = Criteria.create().eq("manufacturer", manufacturer).page(2).item(2);
        List<Product> orderedPage = prdRepo.findByCriteria(c2);
        assertTrue("current = " + orderedPage.size(), 2 == orderedPage.size());
        assertEquals(new Long(80), orderedPage.get(0).getSalePrice());
        assertEquals(new Long(70), orderedPage.get(1).getSalePrice());

        /**
         * 파라미터 필터링
         * 1. 판매가능일시 체크
         * 2. 상품 상태 체크 (= OP)
         */
        prdRepo.save(createProduct(getDate(2014, 1, 1), getDate(2014, 1, 2), manufacturer));
        Product np = createProduct(getDate(2014, 1, 1), getDate(2014, 12, 31), manufacturer);
        np.setStatus(ProductStatus.SO); // 일시품절 상태
        prdRepo.save(np);
        assertTrue(7 == prdRepo.findAllByCriteria().size()); // 추가됐는지 확인

        // 필터링 됐는지 체크
        Criteria c3 = Criteria.create().eq("manufacturer", manufacturer).page(1).item(10);
        assertTrue("current = " + prdRepo.findByCriteria(c3).size(), 5 == prdRepo.findByHql(c3).size());

        List<Product> filteredList = prdRepo.findByCriteria(c2);
        assertTrue(2 == filteredList.size());
        assertEquals(new Long(80), filteredList.get(0).getSalePrice());
        assertEquals(new Long(70), filteredList.get(1).getSalePrice());

    }

    private Product createProduct(Date saleStartDate, Date saleEndDate, String manufacturer) {
        return new Product(manufacturer, 100L, "맥북 2014 late", "최고급 맥북 14년 late 버전", saleStartDate, saleEndDate);
    }
}
