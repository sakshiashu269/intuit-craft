package com.craft.manageOrders.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "Product")
public class Product {
    private String productId;
    private String productName;
    private double unitPrice;
    private float productStock;
    private ProductAvailability productAvailability;
    private ProductType productType;
    private Map<String, Object> attributes;

    public Product() {
    }

    public Product(String productId, String productName, double unitPrice, float productStock, ProductAvailability productAvailability, Map<String, Object> attributes) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.productStock = productStock;
        this.productAvailability = productAvailability;
        this.attributes = attributes;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
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
