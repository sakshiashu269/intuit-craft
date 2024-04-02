package com.craft.manageOrders.order;

import com.craft.manageOrders.invoice.Invoice;
import com.craft.manageOrders.invoice.InvoiceService;
import com.craft.manageOrders.invoice.InvoiceServiceImpl;
import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.order.OrderRepository;
import com.craft.manageOrders.user.User;
import com.craft.manageOrders.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private final InvoiceService invoiceService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    public OrderConsumer(InvoiceService invoiceService, OrderRepository orderRepository, UserService userService) {
        this.invoiceService = invoiceService;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }
    @KafkaListener(topics = "orderTopic", groupId = "my-consumer-group")
    public void receiveOrder(Order order) {
        logger.info("Order will be processed");
        Invoice invoice = invoiceService.generateInvoice(order);
        userService.updateUserOrders(order);
        order.setInvoice(invoice);
        order.setOrderStatus(OrderStatus.INPROGRESS);
        orderRepository.save(order);
    }
}
