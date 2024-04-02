package com.craft.manageOrders;

import com.craft.manageOrders.exceptions.MessageQueueFailureException;
import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.order.OrderProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

class KafkaQueueTest {

    @Mock
    private KafkaTemplate<String, Order> kafkaTemplate;

    @InjectMocks
    private OrderProducer orderProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrder() {
        // Create a sample order
        Order order = new Order();
        order.setBillAmount(100.0);
        order.setOrderId("test");

        // Calling the processOrder method
        try {
            orderProducer.processOrder(order);
        } catch (MessageQueueFailureException e) {
            Assertions.fail(e.getMessage());
        }
    }
}

