package com.craft.manageOrders.order;

import com.craft.manageOrders.exceptions.CartEmptyException;
import com.craft.manageOrders.exceptions.PaymentProcessingException;
import com.craft.manageOrders.exceptions.UserNotFoundException;
import com.craft.manageOrders.payment.PaymentMode;
import com.craft.manageOrders.payment.PaymentService;
import com.craft.manageOrders.product.ProductService;
import com.craft.manageOrders.user.UserRepository;
import com.craft.manageOrders.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final OrderProducer orderProducer;

    @Autowired
    public OrderServiceImpl(PaymentService paymentService, ProductService productService, UserRepository userRepository, OrderProducer orderProducer) {
        this.paymentService = paymentService;
        this.productService = productService;
        this.userRepository = userRepository;
        this.orderProducer = orderProducer;
    }

    @Override
    public String createOrderFromCart(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User Not Found: " + userId);
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
            //Update user orders
            List<String> userOrders = user.getOrders();
            userOrders.add(orderId);
            user.setOrders(userOrders);
            //Update user Cart
            user.getCart().emptyCart();

            //ToDo Update user order and cart in DB
            orderProducer.processOrder(order);

        }
        else{
            productService.increaseCountInProductStock(productVsUnits);
            throw new PaymentProcessingException("Unable to make payment, please try again!");
        }
        return orderId;
    }
}


