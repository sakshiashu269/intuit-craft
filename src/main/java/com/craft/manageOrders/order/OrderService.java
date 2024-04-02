package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.MessageQueueFailureException;

public interface OrderService {
    String createOrderFromCart(String userId) throws MessageQueueFailureException;
}
