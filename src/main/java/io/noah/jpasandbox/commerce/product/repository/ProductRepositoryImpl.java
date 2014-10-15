package io.noah.jpasandbox.commerce.product.repository;

import io.noah.jpasandbox.commerce.product.model.Product;
import io.noah.jpasandbox.commerce.product.model.ProductStatus;
import io.noah.jpasandbox.framework.Criteria;
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
 * Created by chanwook on 2014. 10. 6..
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext(unitName = "pu-sandbox")
    private EntityManager em;

    @Override
    public List<Product> findByHql(Criteria c) {
        // TODO SQL builder
        // TODO Named Query
        String sql =
                " SELECT " +
                        "   p " +
                        " FROM " +
                        "   io.noah.jpasandbox.commerce.product.model.Product p " +
                        " WHERE " +
                        "   p.manufacturer = :manufacturer " +
                        " AND " +
                        "   p.saleStartDate < CURRENT_DATE " +
                        " AND " +
                        "   p.saleEndDate > CURRENT_DATE " +
                        " AND " +
                        "   p.status = \'OP\' " +
                        " ORDER BY p.salePrice DESC ";

        TypedQuery<Product> q = em.createQuery(sql, Product.class);
        q.setParameter("manufacturer", c.get("manufacturer"));
        setPaging(c, q);

        List<Product> resultList = q.getResultList();
        return resultList;
    }

    private void setPaging(Criteria c, TypedQuery<Product> q) {
        // 페이징 설정
        if (c.hasPaging()) {
            q.setFirstResult((c.getPage() - 1) * c.getItemSize());
            q.setMaxResults(c.getItemSize());
        }
    }

    @Override
    public List<Product> findByCriteria(Criteria c) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);

        Root<Product> r = q.from(Product.class);
        ParameterExpression<String> param1 = cb.parameter(String.class);
        ParameterExpression<ProductStatus> param2 = cb.parameter(ProductStatus.class);
        ParameterExpression<Date> param3 = cb.parameter(Date.class);
        ParameterExpression<Date> param4 = cb.parameter(Date.class);

        q.select(r)
            .where(cb.equal(r.get("manufacturer"), param1),
                    cb.equal(r.get("status"), param2),
                    cb.greaterThan(r.<Date>get("saleEndDate"), param3),
                    cb.lessThan(r.<Date>get("saleStartDate"), param3))
            .orderBy(cb.desc(r.get("salePrice")));

        TypedQuery<Product> query = em.createQuery(q);
        query.setParameter(param1, c.get("manufacturer"));
        query.setParameter(param2, ProductStatus.OP);
        query.setParameter(param3, new Date());

        setPaging(c, query);

        List<Product> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Product> findAllByHql() {
        // TODO SQL builder
        // TODO Named Query
        String sql = " SELECT p from io.noah.jpasandbox.commerce.product.model.Product p ORDER BY p.id ASC";
        TypedQuery<Product> q = em.createQuery(sql, Product.class);
        List<Product> resultList = q.getResultList();
        return resultList;
    }

    @Override
    public List<Product> findAllByCriteria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> r = q.from(Product.class);
        q.select(r);

        List<Product> resultList = em.createQuery(q).getResultList();
        return resultList;
    }

    @Override
    public Product save(Product p) {
        em.persist(p);
        return p;
    }

    @Override
    public Product update(Product p) {
        Product mergedProduct = em.merge(p);
        return mergedProduct;
    }

    @Override
    public void remove(Product product) {
        em.remove(product);
    }

}
