package io.noah.jpasandbox;

import io.noah.jpasandbox.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by chanwook on 2014. 10. 22..
 */
@Repository("criteriaDao")
public class ProductCriteriaJpaDao implements ProductDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> find() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<Product> r = query.from(Product.class);
        query.select(r);

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> find(int pageNumber, int itemSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        ParameterExpression<Date> paramOpen = cb.parameter(Date.class);
        ParameterExpression<Date> paramClose = cb.parameter(Date.class);

        Root<Product> r = query.from(Product.class);
        query
                .select(r)
                .where(
                        cb.lessThanOrEqualTo(r.<Date>get("saleOpen"), paramOpen),
                        cb.greaterThanOrEqualTo(r.<Date>get("saleClose"), paramClose))
                .orderBy(cb.desc(r.get("salePrice")))
        ;


        TypedQuery<Product> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemSize);
        typedQuery.setMaxResults(itemSize);

        Date currentDate = new Date();
        typedQuery.setParameter(paramOpen, currentDate);
        typedQuery.setParameter(paramClose, currentDate);

        return typedQuery.getResultList();
    }

    @Override
    public List<Product> find(int pageNumber, int itemSize, String category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        ParameterExpression<Date> paramOpen = cb.parameter(Date.class);
        ParameterExpression<Date> paramClose = cb.parameter(Date.class);
        ParameterExpression<String> paramCategory = cb.parameter(String.class);

        Root<Product> r = query.from(Product.class);
        query
                .select(r)
                .where(
                        cb.lessThanOrEqualTo(r.<Date>get("saleOpen"), paramOpen),
                        cb.greaterThanOrEqualTo(r.<Date>get("saleClose"), paramClose),
                        cb.equal(r.get("category"), paramCategory))
                .orderBy(cb.desc(r.get("salePrice")))
        ;


        TypedQuery<Product> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemSize);
        typedQuery.setMaxResults(itemSize);

        Date currentDate = new Date();
        typedQuery.setParameter(paramOpen, currentDate);
        typedQuery.setParameter(paramClose, currentDate);
        typedQuery.setParameter(paramCategory, category);

        return typedQuery.getResultList();
    }
}
