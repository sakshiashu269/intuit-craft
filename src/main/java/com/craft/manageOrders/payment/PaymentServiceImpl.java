package com.craft.manageOrders.payment;

import com.craft.manageOrders.exceptions.PaymentProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentFactory paymentFactory;
    @Autowired
    public PaymentServiceImpl(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    @Override
    public Boolean processPayment(double paymentAmount, PaymentMode paymentMode) {
        Payment payment = paymentFactory.getPayment(paymentMode);
        if(paymentMode == PaymentMode.CASH){
            System.out.println("Payment is pending as order is COD, payment amount " + paymentAmount);
            return true;
        }
        boolean isPaymentSuccess = payment.pay(paymentAmount);
        if(isPaymentSuccess) {
            System.out.println("A third party payment service will open up for gateway: " + payment + " and payment is done of payment amount " + paymentAmount);
            return true;
        }
        else{
            throw new PaymentProcessingException("Unable to make payment, please try again!");
        }
    }
}
