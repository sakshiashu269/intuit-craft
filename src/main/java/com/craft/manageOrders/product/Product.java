package com.craft.manageOrders.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Product")
public class Product {
    private String productId;
    private String productName;
    private double unitPrice;
    private float productStock;
    private ProductAvailability productAvailability;

    public Product() {
    }

    public ProductAvailability getProductAvailability() {
        return productAvailability;
    }
    public void setProductAvailability(ProductAvailability productAvailability) {
        this.productAvailability = productAvailability;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
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

    public float getProductStock() {
        return productStock;
    }

    public void setProductStock(float productStock) {
        this.productStock = productStock;
    }
}
