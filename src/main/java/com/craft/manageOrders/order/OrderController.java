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
    public ResponseEntity<String> placeOrder(@PathVariable String userId) {
        try {
            String orderId = orderService.createOrderFromCart(userId);
            if (orderId != null) {
                logger.info("Order placed successfully with orderId: {}", orderId);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Order placed successfully. Order ID: " + orderId);
            } else {
                logger.error("Failed to place order for user ID: {}", userId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to place order. Please try again later.");
            }
        } catch (MessageQueueFailureException ex) {
            logger.error("Failed to place order due to message queue failure for user ID: {}", userId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to place order due to an internal error. Please try again later.");
        } catch (Exception ex) {
            logger.error("Failed to place order for user ID: {}", userId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to place order due to an unexpected error. Please try again later.");
        }
    }

}
