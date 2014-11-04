package io.noah.jpasandbox.model;

import javax.persistence.*;

/**
 * Created by chanwook on 2014. 10. 31..
 */
@Entity
@Table(name = "PRD_PRD_ITEM_M")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    @Column(name = "ITEM_NAME", nullable = false, length = 100)
    private String itemName;

    @Column(name = "EXT_PRICE", nullable = false)
    private long extraPrice;

    @Column(name = "STOCK", nullable = false)
    private long stock;

    public ProductItem() {
    }

    public ProductItem(String itemName, long extraPrice, long stock) {
        this.itemName = itemName;
        this.extraPrice = extraPrice;
        this.stock = stock;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(long extraPrice) {
        this.extraPrice = extraPrice;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
