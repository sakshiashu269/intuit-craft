package com.craft.manageOrders.payment;


import com.craft.manageOrders.invoice.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Cash implements Payment {
    Logger logger = LoggerFactory.getLogger(Cash.class);
    @Override
    public boolean pay(double amount) {
        logger.info("User will be paying using Cash");
        //after some processing
        return true;
    }
}
