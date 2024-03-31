package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.MessageQueueFailureException;
import com.craft.manageOrders.product.ProductServiceImpl;
import com.craft.manageOrders.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    List<Order> orders = new ArrayList<Order>();
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public void addOrder(Order order) {
        orders.add(order);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
    }


    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable String userId) throws MessageQueueFailureException {
        String orderId = orderService.createOrderFromCart(userId);
        if (orderId != null) {
            logger.info("Order is placed successfully with orderId: " + orderId);
            return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
        } else {
            logger.error("Failed to place order" + orderId);
            return new ResponseEntity<>("Failed to place order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
