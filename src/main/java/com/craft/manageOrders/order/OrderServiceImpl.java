package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.PaymentProcessingException;
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
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final ProductService productService;
    private final OrderProducer orderProducer;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(PaymentService paymentService, ProductService productService, OrderProducer orderProducer, UserService userService) {
        this.paymentService = paymentService;
        this.productService = productService;
        this.orderProducer = orderProducer;
        this.userService = userService;
    }

    @Override
    public String createOrderFromCart(String userId) {
        User user = userService.findUser(userId);

        //validating productStock and updating stocks
        Map<String, Integer> productVsUnits = user.getCart().getProductVsUnits();
        productService.decreaseCountFromProductStock(productVsUnits);

        //new order creation
        String orderId = createNewOrderId();
        Order order = createNewOrder(orderId, user);

         //Call payment service to process payment, hardcoding cash here but in real time we will take user input
        boolean paymentSuccess = paymentService.processPayment(order.getBillAmount(), PaymentMode.CASH);

        if(paymentSuccess) {
            //updating user's cart
            userService.updateUserOrders(userId, orderId);
            try{
                //Queuing the order to process further
                orderProducer.processOrder(order);
            }
            catch (Exception e){
                logger.error("Unable to process order due to exception " + e.getMessage());
            }
        }
        else{
            productService.increaseCountInProductStock(productVsUnits);
            throw new PaymentProcessingException("Unable to make payment, please try again!");
        }
        return orderId;
    }

    private Order createNewOrder(String orderId, User user) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(user.getUserId());
        order.setProductVsUnits(user.getCart().getProductVsUnits());
        order.setAddress(user.getAddress());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setBillAmount(user.getCart().getTotalPrice());
        order.setPaymentMode(PaymentMode.CASH);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private String createNewOrderId() {
        return UUID.randomUUID().toString();
    }
}


