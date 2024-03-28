package com.craft.manageOrders.payment;


import org.springframework.stereotype.Component;

@Component
public class Cash implements Payment {
    @Override
    public void pay() {
        System.out.println("User has paid using UPI");
    }
}
