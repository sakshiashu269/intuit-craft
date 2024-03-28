package com.craft.manageOrders.factory;

import com.craft.manageOrders.entity.Card;
import com.craft.manageOrders.entity.Cash;
import com.craft.manageOrders.entity.Payment;
import com.craft.manageOrders.entity.PaymentMode;
import org.springframework.beans.factory.annotation.Autowired;

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
