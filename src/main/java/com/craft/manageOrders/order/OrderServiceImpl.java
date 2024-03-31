package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.CartEmptyException;
import com.craft.manageOrders.exceptions.MessageQueueFailureException;
import com.craft.manageOrders.exceptions.PaymentProcessingException;
import com.craft.manageOrders.exceptions.UserNotFoundException;
import com.craft.manageOrders.payment.PaymentMode;
import com.craft.manageOrders.payment.PaymentService;
import com.craft.manageOrders.product.ProductService;
import com.craft.manageOrders.user.UserRepository;
import com.craft.manageOrders.user.User;
import com.craft.manageOrders.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final OrderProducer orderProducer;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(PaymentService paymentService, ProductService productService, UserRepository userRepository, OrderProducer orderProducer, UserService userService) {
        this.paymentService = paymentService;
        this.productService = productService;
        this.userRepository = userRepository;
        this.orderProducer = orderProducer;
        this.userService = userService;
    }

    @Override
    public String createOrderFromCart(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("Failed to fetch user details " + userId);
        }
        Map<String, Integer> productVsUnits = user.getCart().getProductVsUnits();

        if (productVsUnits.isEmpty()) {
            throw new CartEmptyException("Cart Empty, Please select Products");
        }

        productService.decreaseCountFromProductStock(productVsUnits);

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, productVsUnits, user.getAddress(), user.getCart().getTotalPrice(), OrderStatus.PENDING, LocalDate.now(), PaymentMode.CASH);

         //Call payment service to process payment
        boolean paymentSuccess = paymentService.processPayment(order.getBillAmount(), PaymentMode.CASH);

        if(paymentSuccess) {
            userService.updateUserOrders(userId, orderId);
            try{
                orderProducer.processOrder(order);
            }
            catch (Exception e){
                logger.error("Error processing order, message queue failure exception: " + e.getMessage());
            }
        }
        else{
            productService.increaseCountInProductStock(productVsUnits);
            throw new PaymentProcessingException("Unable to make payment, please try again!");
        }
        return orderId;
    }
}


