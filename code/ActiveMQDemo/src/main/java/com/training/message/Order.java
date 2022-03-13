package com.training.message;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import java.io.Serializable;

public class Order implements Serializable {

    private String productName;
    private Integer qty;

    public Order(String productName, Integer qty) {
        this.productName = productName;
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Message{" +
                "productName='" + productName + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
