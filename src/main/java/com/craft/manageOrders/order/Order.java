package com.craft.manageOrders.order;

import com.craft.manageOrders.Address;
import com.craft.manageOrders.invoice.Invoice;
import com.craft.manageOrders.payment.PaymentMode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "Order")
public class Order {
    private String orderId;
    private String userId;
    private Map<String, Integer> productVsUnits = new HashMap<>();
    private Address address;
    private double billAmount;
    private Invoice invoice;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private PaymentMode paymentMode;
    public Order() {
    }
    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Integer> getProductVsUnits() {
        return productVsUnits;
    }

    public void setProductVsUnits(Map<String, Integer> productVsUnits) {
        this.productVsUnits = productVsUnits;
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
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public Order(String orderId, String userId, Map<String, Integer> productVsUnits, Address address, double billAmount, OrderStatus orderStatus, LocalDate orderDate, PaymentMode paymentMode) {
        this.orderId = orderId;
        this.userId = userId;
        this.productVsUnits = productVsUnits;
        this.address = address;
        this.billAmount = billAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.paymentMode = paymentMode;
    }
}