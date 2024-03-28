package com.craft.manageOrders.payment;

import org.springframework.stereotype.Component;

@Component
public class Card implements Payment {
    public void pay() {
        System.out.println("User has paid using card");
    }
}
