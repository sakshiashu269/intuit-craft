package com.craft.manageOrders.order;

import com.craft.manageOrders.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    @Autowired
    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processOrder(Order order) {
        kafkaTemplate.send("orderTopic", order);
    }
}
