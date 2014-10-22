package io.noah.jpasandbox.step1;

import java.util.List;

/**
 * Created by chanwook on 2014. 10. 22..
 */
public interface ProductDao {
    List<Product> find();

    List<Product> find(int pageNumber, int itemSize);

    List<Product> find(int pageNumber, int itemSize, String category);
}
