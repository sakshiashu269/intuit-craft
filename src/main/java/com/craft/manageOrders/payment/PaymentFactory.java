package com.craft.manageOrders.payment;

import com.craft.manageOrders.payment.Card;
import com.craft.manageOrders.payment.Cash;
import com.craft.manageOrders.payment.Payment;
import com.craft.manageOrders.payment.PaymentMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {
    private final Card card;
    private final Cash cash;

    @Autowired
    public PaymentFactory(Card card, Cash cash) {
        this.card = card;
        this.cash = cash;
    }

    public Payment getPayment(PaymentMode paymentMode) {
        switch (paymentMode) {
            case CARD:
                return card;
            case CASH:
                return cash;
            default:
                throw new IllegalArgumentException("Unsupported payment mode: " + paymentMode);
        }
    }
}
