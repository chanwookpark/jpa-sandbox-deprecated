package io.noah.jpasandbox.commerce.product.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "SALE_PRICE", insertable = true, length = 10, nullable = false)
    private Long salePrice;

    @Column(name = "PRD_NAME", insertable = true, length = 100, nullable = false)
    private String productName;

    @Column(name = "DISPLAY_NAME", insertable = true, length = 255, nullable = false)
    private String displayName;

    @Column(name = "SALE_START_DATE", insertable = true, nullable = false)
    private Date saleStartDate;

    @Column(name = "SALE_END_DATE", insertable = true, nullable = false)
    private Date saleEndDate;

    public Product() {
    }

    public Product(String manufacturer, Long salePrice, String productName, String displayName, Date saleStartDate, Date saleEndDate) {
        this.manufacturer = manufacturer;
        this.salePrice = salePrice;
        this.productName = productName;
        this.displayName = displayName;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
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

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
