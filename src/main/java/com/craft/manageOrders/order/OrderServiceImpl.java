package com.craft.manageOrders.order;

import com.craft.manageOrders.payment.PaymentMode;
import com.craft.manageOrders.payment.PaymentService;
import com.craft.manageOrders.product.ProductService;
import com.craft.manageOrders.user.UserRepository;
//import com.craft.manageOrders.invoice.InvoiceService;
import com.craft.manageOrders.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final UserRepository userRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PaymentService paymentService, ProductService productService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean createOrderFromCart(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User Not Found: " + userId);
        }
        Map<String, Integer> productVsUnits = user.getCart().getProductVsUnits();

        if (productVsUnits.isEmpty()) {
            throw new RuntimeException("Cart Empty, Please select Products");
        }

        productService.decreaseCountFromProductStock(productVsUnits);

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, productVsUnits, user.getAddress(), user.getCart().getTotalPrice(), OrderStatus.PENDING, LocalDate.now(), PaymentMode.CASH);

         //Call payment service to process payment
        boolean paymentSuccess = paymentService.processPayment(order.getBillAmount(), PaymentMode.CASH);

        //ToDo: Add order in orderlist of user
        List<String> userOrders = user.getOrders();
        userOrders.add(orderId);
        user.setOrders(userOrders);

        if(paymentSuccess) {
            user.getCart().emptyCart();
            orderRepository.save(order);
            //ToDo: Create Invoice
        }
        else{
            productService.increaseCountInProductStock(productVsUnits);
            throw new RuntimeException("Unable to make payment, please try again!");
        }
        return true;
    }
}


