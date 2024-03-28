package com.craft.manageOrders.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "Invoice")
public class Invoice {
    @Id
    @Field("_id")
    private String invoiceId;
    private String userName;
    private String orderId;
    private Address address;
    private double billAmount;
    private Map<Integer, Integer> productVsUnits = new HashMap<>();

    public Invoice(String invoiceId, String userName, String orderId, Address address, double billAmount, Map<Integer, Integer> productVsUnits) {
        this.invoiceId = invoiceId;
        this.userName = userName;
        this.orderId = orderId;
        this.address = address;
        this.billAmount = billAmount;
        this.productVsUnits = productVsUnits;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Map<Integer, Integer> getProductVsUnits() {
        return productVsUnits;
    }

    public void setProductVsUnits(Map<Integer, Integer> productVsUnits) {
        this.productVsUnits = productVsUnits;
    }
}
