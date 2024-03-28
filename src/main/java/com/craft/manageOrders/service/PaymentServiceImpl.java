package com.craft.manageOrders.service;

import com.craft.manageOrders.entity.Payment;
import com.craft.manageOrders.entity.PaymentMode;
import com.craft.manageOrders.factory.PaymentFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentFactory paymentFactory;

    @Override
    public Boolean createPayment(double paymentAmount, PaymentMode paymentMode) {
        Payment payment = paymentFactory.getPayment(paymentMode);
        System.out.println("A third party payment service will open up for gateway: " + payment + " and payment is done of payment amount " + paymentAmount);
        return true;
    }
}
