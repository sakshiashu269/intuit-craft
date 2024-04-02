package com.craft.manageOrders.payment;

import com.craft.manageOrders.invoice.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Card implements Payment {
    Logger logger = LoggerFactory.getLogger(Card.class);
    @Override
    public boolean pay(double amount) {
        logger.info("User has paid using card");
        //calling third party payment gateway to process payment
        return true;
    }
}
