package io.noah.jpasandbox.step1;

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
 * Created by chanwook on 2014. 11. 3..
 */
@Repository("criteriaDao")
public class CriteriaProductDao implements ProductDao {

    @PersistenceContext(name = "pu-sandbox")
    private EntityManager em;

    @Override
    public List<Product> find() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<Product> r = query.from(Product.class);
        query.select(r);

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> find(int itemSize, int pageNumber) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        /* Query 생성 */
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        ParameterExpression<Date> paramOpen = cb.parameter(Date.class);
        ParameterExpression<Date> paramClose = cb.parameter(Date.class);

        Root<Product> r = query.from(Product.class);
        query.select(r)
                .where(
                        cb.greaterThan(r.<Date>get("saleClose"), paramClose),
                        cb.lessThan(r.<Date>get("saleOpen"), paramOpen))
                .orderBy(cb.desc(r.get("salePrice")));

        TypedQuery<Product> typedQuery = em.createQuery(query);
        /* Paging 적용 */
        typedQuery.setFirstResult((pageNumber - 1) * itemSize);
        typedQuery.setMaxResults(itemSize);

        Date currentDate = new Date();
        typedQuery.setParameter(paramClose, currentDate);
        typedQuery.setParameter(paramOpen, currentDate);

        return typedQuery.getResultList();
    }

    @Override
    public List<Product> find(int itemSize, int pageNumber, String category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        /* Query 생성 */
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        ParameterExpression<Date> paramOpen = cb.parameter(Date.class);
        ParameterExpression<Date> paramClose = cb.parameter(Date.class);
        ParameterExpression<String> paramCategory = cb.parameter(String.class);

        Root<Product> r = query.from(Product.class);
        query.select(r)
                .where(
                        cb.greaterThan(r.<Date>get("saleClose"), paramClose),
                        cb.lessThan(r.<Date>get("saleOpen"), paramOpen),
                        cb.equal(r.get("category"),paramCategory))
                .orderBy(cb.desc(r.get("salePrice")));

        TypedQuery<Product> typedQuery = em.createQuery(query);
        /* Paging 적용 */
        typedQuery.setFirstResult((pageNumber - 1) * itemSize);
        typedQuery.setMaxResults(itemSize);

        Date currentDate = new Date();
        typedQuery.setParameter(paramClose, currentDate);
        typedQuery.setParameter(paramOpen, currentDate);
        typedQuery.setParameter(paramCategory, category);

        return typedQuery.getResultList();
    }
}
