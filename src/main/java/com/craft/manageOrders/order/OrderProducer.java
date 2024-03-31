package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.MessageQueueFailureException;
import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.product.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    @Autowired
    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processOrder(Order order) throws MessageQueueFailureException {
        try {
            kafkaTemplate.send("orderTopic", order);
        } catch (Exception e) {
            logger.error("Send message failed - " + e.getMessage());
            throw new MessageQueueFailureException(e.getMessage());
        }
    }
}
