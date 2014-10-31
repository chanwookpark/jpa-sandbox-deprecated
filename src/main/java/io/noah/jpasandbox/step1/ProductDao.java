package io.noah.jpasandbox.step1;

import java.util.List;

/**
 * Created by chanwook on 2014. 10. 31..
 */
public interface ProductDao {

    List<Product> find();

    List<Product> find(int itemSize, int pageNumber);

    List<Product> find(int itemSize, int pageNumber, String category);
}
