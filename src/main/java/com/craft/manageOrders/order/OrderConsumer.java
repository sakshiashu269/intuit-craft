package com.craft.manageOrders.order;

import com.craft.manageOrders.invoice.Invoice;
import com.craft.manageOrders.invoice.InvoiceService;
import com.craft.manageOrders.invoice.InvoiceServiceImpl;
import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.order.OrderRepository;
import com.craft.manageOrders.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private final InvoiceService invoiceService;
    private final OrderRepository orderRepository;
    Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    public OrderConsumer(InvoiceService invoiceService, OrderRepository orderRepository) {
        this.invoiceService = invoiceService;
        this.orderRepository = orderRepository;
    }


    @KafkaListener(topics = "orderTopic", groupId = "my-consumer-group")
    public void receiveOrder(Order order) {
        logger.info("Order will be processed");
        Invoice invoice = invoiceService.generateInvoice(order);
        order.setInvoice(invoice);
        order.setOrderStatus(OrderStatus.INPROGRESS);
        orderRepository.save(order);
    }
}
