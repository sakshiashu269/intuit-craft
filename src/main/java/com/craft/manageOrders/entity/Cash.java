package com.craft.manageOrders.entity;

public class Cash implements Payment {
    @Override
    public void pay() {
        System.out.println("User has paid using UPI");
    }
}
