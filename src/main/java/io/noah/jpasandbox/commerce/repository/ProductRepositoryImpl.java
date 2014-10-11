package io.noah.jpasandbox.commerce.repository;

import io.noah.jpasandbox.commerce.Product;
import io.noah.jpasandbox.framework.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext(unitName = "pu-sandbox")
    private EntityManager em;

    @Override
    public List<Product> findByCriteria(Criteria c) {
        TypedQuery<Product> q = em.createQuery(
                " SELECT p from io.noah.jpasandbox.commerce.Product p WHERE p.manufacturer = :manufacturer ORDER BY p.id ", Product.class);
        q.setParameter("manufacturer", c.get("manufacturer"));
        List<Product> resultList = q.getResultList();

        return resultList;
    }

    @Override
    public Product save(Product p) {
        return null;
    }

    @Override
    public Product update(Product p) {
        return null;
    }

    @Override
    public boolean remove(Product updatedProduct) {
        return false;
    }
}
