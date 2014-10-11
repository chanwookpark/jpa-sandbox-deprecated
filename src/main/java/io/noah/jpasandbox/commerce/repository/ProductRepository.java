package io.noah.jpasandbox.commerce.repository;

import io.noah.jpasandbox.commerce.Product;
import io.noah.jpasandbox.framework.Criteria;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public interface ProductRepository {

    java.util.List<Product> findByCriteria(Criteria c);

    Product save(Product p);

    Product update(Product p);

    void remove(Product product);
}
