package com.craft.manageOrders.invoice;

import com.craft.manageOrders.Address;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Invoice {
    private String invoiceId;
    private String userId;
    private String orderId;
    private Address address;
    private double totalItemPrice;
    private double totalTax;
    private double finalBillAmount;
    private Map<String, Integer> productVsUnits = new HashMap<>();

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Map<String, Integer> getProductVsUnits() {
        return productVsUnits;
    }

    public void setProductVsUnits(Map<String, Integer> productVsUnits) {
        this.productVsUnits = productVsUnits;
    }
    public double getTotalItemPrice() {
        return totalItemPrice;
    }
    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }
    public double getTotalTax() {
        return totalTax;
    }
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
    public double getFinalBillAmount() {
        return finalBillAmount;
    }
    public void setFinalBillAmount(double finalBillAmount) {
        this.finalBillAmount = finalBillAmount;
    }

}
