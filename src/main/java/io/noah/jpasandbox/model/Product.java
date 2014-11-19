package io.noah.jpasandbox.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by chanwook on 2014. 10. 21..
 */
@Entity
@Table(name = "PRD_PRODUCT_M")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false)
    private long salePrice;


    @Temporal(TemporalType.TIMESTAMP)
    private Date saleOpen;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saleClose;

    public Product() {
    }

    public Product(String name, String category, long salePrice, Date saleOpen, Date saleClose) {
        this.name = name;
        this.category = category;
        this.salePrice = salePrice;
        this.saleOpen = saleOpen;
        this.saleClose = saleClose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public Date getSaleOpen() {
        return saleOpen;
    }

    public void setSaleOpen(Date saleOpen) {
        this.saleOpen = saleOpen;
    }

    public Date getSaleClose() {
        return saleClose;
    }

    public void setSaleClose(Date saleEnd) {
        this.saleClose = saleEnd;
    }

}
