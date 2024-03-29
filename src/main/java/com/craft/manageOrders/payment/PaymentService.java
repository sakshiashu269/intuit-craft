package com.craft.manageOrders.payment;

import com.craft.manageOrders.payment.PaymentMode;

public interface PaymentService {
    Boolean processPayment(double paymentAmount, PaymentMode paymentMode);
}
