package com.craft.manageOrders.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Product")
public class Product {
    private int productId;
    private String productName;
    private double unitPrice;
    private ProductAvailability productAvailability;
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public ProductAvailability getProductAvailability() {
        return productAvailability;
    }
    public void setProductAvailability(ProductAvailability productAvailability) {
        this.productAvailability = productAvailability;
    }
}
