package com.craft.manageOrders.exceptions;
public class MessageQueueFailureException extends Exception {
    public MessageQueueFailureException(String message) {
        super(message);
    }
}
