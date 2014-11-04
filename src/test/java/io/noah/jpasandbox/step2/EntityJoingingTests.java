package io.noah.jpasandbox.step2;

import io.noah.jpasandbox.ProductTestSupport;
import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.junit.Test;

import static io.noah.jpasandbox.util.DateUtil.toDate;

/**
 * Created by chanwook on 2014. 10. 1..
 */
public class EntityJoingingTests extends ProductTestSupport {

    @Test
    public void oneToMany() throws Exception {
        Product p = new Product("맥북에어", "노트북", 1000, toDate("2014-01-01 09:00:00"), toDate("2014-12-30 11:59:59"));
        ProductItem item1 = new ProductItem("11인치 기본형", 0, 100);
        ProductItem item2 = new ProductItem("11인치 고급형", 100, 100);
        ProductItem item3 = new ProductItem("13인치 기본형", 500, 100);
        ProductItem item4 = new ProductItem("13인치 고급형", 1000, 100);
        p.addItem(item1);
        p.addItem(item2);
        p.addItem(item3);
        p.addItem(item4);

        // 기본 설정(@OneToMany)일 때 Product를 가지고 오면??
    }

}
