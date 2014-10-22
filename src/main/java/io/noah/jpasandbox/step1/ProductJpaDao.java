package io.noah.jpasandbox.step1;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by chanwook on 2014. 10. 22..
 */
@Repository
public class ProductJpaDao implements ProductDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> find() {
        Query query = em.createQuery(" SELECT p FROM Product p ");
        return query.getResultList();
    }

    @Override
    public List<Product> find(int pageNumber, int itemSize) {
        String queryText =
                " SELECT p FROM Product p " +
                " WHERE p.saleOpen < CURRENT_DATE AND p.saleEnd > CURRENT_DATE " +
                " ORDER BY p.salePrice desc ";

        Query query = em.createQuery(queryText);
        // pageNumber는 1부터 시작한다는 가정
        query.setFirstResult((pageNumber - 1) * itemSize);
        query.setMaxResults(itemSize);

        return query.getResultList();
    }

    @Override
    public List<Product> find(int pageNumber, int itemSize, String category) {
        String queryText =
                " SELECT p FROM Product p " +
                " WHERE p.saleOpen < CURRENT_DATE AND p.saleEnd > CURRENT_DATE AND " +
                "   p.category = :category " +
                " ORDER BY p.salePrice desc ";

        Query query = em.createQuery(queryText);
        // pageNumber는 1부터 시작한다는 가정
        query.setParameter("category", category);
        query.setFirstResult((pageNumber - 1) * itemSize);
        query.setMaxResults(itemSize);

        return query.getResultList();
    }
}
