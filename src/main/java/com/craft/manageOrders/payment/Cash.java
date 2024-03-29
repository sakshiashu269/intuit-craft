package com.craft.manageOrders.payment;


import org.springframework.stereotype.Component;

@Component
public class Cash implements Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("User will be paying using Cash");
        return true;
    }
}
