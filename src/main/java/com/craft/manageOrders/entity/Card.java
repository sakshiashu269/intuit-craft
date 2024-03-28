package com.craft.manageOrders.entity;

public class Card implements Payment {
    public void pay() {
        System.out.println("User has paid using card");
    }
}
