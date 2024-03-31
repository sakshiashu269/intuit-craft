package com.craft.manageOrders.payment;

import com.craft.manageOrders.exceptions.PaymentProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
        boolean isPaymentSuccess = false;
        int retryAttempts = 3; // Resetting retry attempts for payment processing
        boolean paymentSuccess = false;
        while (retryAttempts > 0) {
            try {
                isPaymentSuccess = payment.pay(paymentAmount);
                break; // Exit the loop if successful
            } catch (Exception e) {
                // Log the exception or perform any necessary actions
                retryAttempts--;
                if (retryAttempts == 0) {
                    throw new PaymentProcessingException("Failed to process payment after multiple attempts, please try again!");
                }
                // Exponential backoff before retrying
                try {
                    TimeUnit.SECONDS.sleep((long) Math.pow(2, 3 - retryAttempts));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if(isPaymentSuccess) {
            System.out.println("A third party payment service will open up for gateway: " + payment + " and payment is done of payment amount " + paymentAmount);
            return true;
        }
        else{
            throw new PaymentProcessingException("Unable to make payment, please try again!");
        }
    }
}
