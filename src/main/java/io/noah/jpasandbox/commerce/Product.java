package io.noah.jpasandbox.commerce;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by chanwook on 2014. 10. 3..
 */
@Entity
@Table(name = "PRD_PRODUCT_M")
public class Product implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MANUFACTURE", insertable = true, length = 100, nullable = false, unique = false, updatable = true)
    private String manufacturer;

    public Product() {
    }
    
    public Product(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
