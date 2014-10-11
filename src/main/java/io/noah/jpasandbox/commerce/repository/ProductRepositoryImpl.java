package io.noah.jpasandbox.commerce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext(unitName = "pu-sandbox")
    private EntityManager em;
}
