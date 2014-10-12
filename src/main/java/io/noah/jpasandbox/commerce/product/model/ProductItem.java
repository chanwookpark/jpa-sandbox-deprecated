package io.noah.jpasandbox.commerce.product.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by chanwook on 2014. 10. 13..
 */
@Entity
@Table(name = "PRD_PRD_ITEM_M")
public class ProductItem implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRD_ITEM_NAME", insertable = true, length = 100, nullable = false)
    private String itemName;

    @Column(name = "PRD_ITEM_STATUS", insertable = true, length = 2, nullable = false)
    private ProductItemStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ProductItemStatus getStatus() {
        return status;
    }

    public void setStatus(ProductItemStatus status) {
        this.status = status;
    }
}
