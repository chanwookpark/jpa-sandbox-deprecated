package io.noah.jpasandbox;

import io.noah.jpasandbox.model.Product;
import io.noah.jpasandbox.model.ProductItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by chanwook on 2014. 10. 22..
 */
@Repository("hqlDao")
public class ProductHqlJpaDao implements ProductDao {

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
                " WHERE p.saleOpen < CURRENT_DATE AND p.saleClose > CURRENT_DATE " +
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
                " WHERE p.saleOpen < CURRENT_DATE AND p.saleClose > CURRENT_DATE AND " +
                "   p.category = :category " +
                " ORDER BY p.salePrice desc ";

        Query query = em.createQuery(queryText);
        // pageNumber는 1부터 시작한다는 가정
        query.setParameter("category", category);
        query.setFirstResult((pageNumber - 1) * itemSize);
        query.setMaxResults(itemSize);

        return query.getResultList();
    }

    @Override
    public List<Product> findProductWithItem(long productId) {
        Query query = em.createQuery(
                " SELECT p FROM Product p WHERE p.id = :prdId ",
                Product.class);
        /*
        Query query = em.createQuery(
                " SELECT distinct p FROM Product p join p.itemList itemList WHERE p.id = :prdId ",
                Product.class);
        Query query = em.createQuery(
                " SELECT p FROM Product p join p.itemList il WHERE p.id = :prdId ",
                Product.class);
        */

        query.setParameter("prdId", productId);

        return query.getResultList();
    }

    @Override
    public List<ProductItem> findProductItem(long productId) {
        TypedQuery<ProductItem> query = em.createQuery(
                "SELECT pi FROM ProductItem pi WHERE pi.product.id = :prdId",
                ProductItem.class);
        query.setParameter("prdId", productId);
        return query.getResultList();
    }
}
