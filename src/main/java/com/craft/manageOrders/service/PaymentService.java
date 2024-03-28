package com.craft.manageOrders.service;

import com.craft.manageOrders.entity.Payment;
import com.craft.manageOrders.entity.PaymentMode;

public interface PaymentService {
    Boolean createPayment(double paymentAmount, PaymentMode paymentMode);
}
