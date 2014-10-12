package io.noah.jpasandbox.data;

import io.noah.jpasandbox.commerce.product.model.Product;
import io.noah.jpasandbox.commerce.product.repository.ProductRepository;
import io.noah.jpasandbox.config.JpaContextConfig;
import io.noah.jpasandbox.framework.Criteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.Date;

/**
 * Created by chanwook on 2014. 10. 6..
 */
//TODO 테스트 데이터 생성하는 별도 방식 개발 및 적용... 일단 클래스 생성해서 집어 넣기!
@ContextConfiguration(classes = {JpaContextConfig.class})
public class ProductTestDataCreator extends AbstractJUnit4SpringContextTests {

    @Autowired
    ProductRepository prdRepo;

    @Autowired
    PlatformTransactionManager txManager;

    @Test
    public void simpleProductCrud() throws Exception {

        DefaultTransactionAttribute def = new DefaultTransactionAttribute();
        TransactionStatus tx = txManager.getTransaction(def);

        try {
            // 전체 삭제
            for (Product p : prdRepo.findByCriteria(Criteria.create())) {
                prdRepo.remove(p);
            }

            prdRepo.save(new Product("Apple Inc.", 100L, "맥북프로 15인치 2014 late", "최고급 맥북 프로 15인치 14년 late 버전", new Date(), new Date()));
            prdRepo.save(new Product("Apple Inc.", 80L, "맥북프로 13인치 2014 late", "최고급 맥북 프로 13인치 14년 late 버전", new Date(), new Date()));
            prdRepo.save(new Product("Apple Inc.", 70L, "맥북프로 11인치 2014 late", "최고급 맥북 프로 11인치 14년 late 버전", new Date(), new Date()));
            prdRepo.save(new Product("Apple Inc.", 90L, "맥북프로 15인치 2014 early", "최고급 맥북 프로 15인치 14년 early 버전", new Date(), new Date()));
            prdRepo.save(new Product("Apple Inc.", 60L, "맥북프로 13인치 2014 early", "최고급 맥북 프로 13인치 14년 early 버전", new Date(), new Date()));

            txManager.commit(tx);
        } catch (Exception e) {
            txManager.rollback(tx);
            throw new RuntimeException(e);
        }
    }
}
