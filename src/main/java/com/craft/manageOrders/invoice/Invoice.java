//package com.craft.manageOrders.invoice;
//
//import com.craft.manageOrders.Address;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Document(collection = "Invoice")
//public class Invoice {
//    @Id
//    @Field("_id")
//    private String invoiceId;
//    private String userName;
//    private String orderId;
//    private Address address;
//    private double totalItemPrice;
//    private double totalTax;
//    private double finalBillAmount;
//    private Map<String, Integer> productVsUnits = new HashMap<>();
//
//
//    public Invoice(String invoiceId, String userName, String orderId, Address address, double totalItemPrice, double totalTax, double finalBillAmount, Map<String, Integer> productVsUnits) {
//        this.invoiceId = invoiceId;
//        this.userName = userName;
//        this.orderId = orderId;
//        this.address = address;
//        this.totalItemPrice = totalItemPrice;
//        this.totalTax = totalTax;
//        this.finalBillAmount = finalBillAmount;
//        this.productVsUnits = productVsUnits;
//    }
//
//    public String getInvoiceId() {
//        return invoiceId;
//    }
//
//    public void setInvoiceId(String invoiceId) {
//        this.invoiceId = invoiceId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public Map<String, Integer> getProductVsUnits() {
//        return productVsUnits;
//    }
//
//    public void setProductVsUnits(Map<String, Integer> productVsUnits) {
//        this.productVsUnits = productVsUnits;
//    }
//    public double getTotalItemPrice() {
//        return totalItemPrice;
//    }
//    public void setTotalItemPrice(double totalItemPrice) {
//        this.totalItemPrice = totalItemPrice;
//    }
//    public double getTotalTax() {
//        return totalTax;
//    }
//    public void setTotalTax(double totalTax) {
//        this.totalTax = totalTax;
//    }
//    public double getFinalBillAmount() {
//        return finalBillAmount;
//    }
//    public void setFinalBillAmount(double finalBillAmount) {
//        this.finalBillAmount = finalBillAmount;
//    }
//
//}
