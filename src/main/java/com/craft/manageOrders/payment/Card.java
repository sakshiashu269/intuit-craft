package com.craft.manageOrders.payment;

import org.springframework.stereotype.Component;

@Component
public class Card implements Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("User has paid using card");
        return true;
    }
}
