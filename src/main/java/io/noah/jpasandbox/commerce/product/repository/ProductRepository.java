package io.noah.jpasandbox.commerce.product.repository;

import io.noah.jpasandbox.commerce.product.model.Product;
import io.noah.jpasandbox.framework.Criteria;

import java.util.List;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public interface ProductRepository {

    List<Product> findByHql(Criteria c);

    List<Product> findByCriteria(Criteria c);

    List<Product> findAllByHql();

    List<Product> findAllByCriteria();

    Product save(Product p);

    Product update(Product p);

    void remove(Product product);

}
