package io.noah.jpasandbox.step1;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by chanwook on 2014. 10. 31..
 */
@Repository
public class HqlProductDao implements ProductDao {

    @PersistenceContext(name = "pu-sandbox")
    private EntityManager em;

    @Override
    public List<Product> find() {
        Query query = em.createQuery(" SELECT p FROM Product p ");
        return query.getResultList();
    }

    @Override
    public List<Product> find(int itemSize, int pageNumber) {
        Query query = em.createQuery(
                " SELECT p FROM Product p " +
                " WHERE p.saleOpen < CURRENT_DATE AND " +
                        "p.saleClose > CURRENT_DATE  " +
                " ORDER BY p.salePrice desc "
        );
        query.setFirstResult((pageNumber - 1) * itemSize);
        query.setMaxResults(itemSize);
        return query.getResultList();
    }

    @Override
    public List<Product> find(int itemSize, int pageNumber, String category) {
        Query query = em.createQuery(
                " SELECT p FROM Product p " +
                " WHERE p.saleOpen < CURRENT_DATE AND " +
                    "p.saleClose > CURRENT_DATE AND " +
                    "p.category = :category " +
                " ORDER BY p.salePrice desc "
        );

        query.setParameter("category", category);
        query.setFirstResult((pageNumber - 1) * itemSize);
        query.setMaxResults(itemSize);

        return query.getResultList();
    }
}
