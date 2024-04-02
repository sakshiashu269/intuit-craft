package com.craft.manageOrders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import com.craft.manageOrders.exceptions.MessageQueueFailureException;
import com.craft.manageOrders.utility.Address;
import com.craft.manageOrders.exceptions.UserNotFoundException;
import com.craft.manageOrders.order.Order;
import com.craft.manageOrders.order.OrderProducer;
import com.craft.manageOrders.order.OrderServiceImpl;
import com.craft.manageOrders.payment.PaymentMode;
import com.craft.manageOrders.payment.PaymentService;
import com.craft.manageOrders.product.ProductService;
import com.craft.manageOrders.cart.Cart;
import com.craft.manageOrders.user.User;
import com.craft.manageOrders.user.UserRepository;
import com.craft.manageOrders.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private UserService userService;

    @Mock
    private OrderProducer orderProducer;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderFromCart_Success() throws MessageQueueFailureException {
        // Mock user, cart, and productVsUnits
        User user = new User();
        user.setUserId("testUser");
        Address address = new Address();
        address.setAddressLine1("123 Main St");
        user.setAddress(address);
        Cart cart = new Cart();
        Map<String, Integer> productVsUnits = new HashMap<>();
        productVsUnits.put("product1", 2); // Assuming product1 with 2 units in cart
        cart.setProductVsUnits(productVsUnits);
        user.setCart(cart);

        when(userService.findUser("testUser")).thenReturn(user);
        when(paymentService.processPayment(anyDouble(), eq(PaymentMode.CASH))).thenReturn(true);

        // Perform the method call
        String orderId = orderServiceImpl.createOrderFromCart("testUser");

        // Verify that the order was created successfully
        assertNotNull(orderId);
        assertFalse(orderId.isEmpty());

        // Verify interactions with other services
        verify(productService).decreaseCountFromProductStock(productVsUnits);
        verify(paymentService).processPayment(anyDouble(), eq(PaymentMode.CASH));
        verify(userService).findUser(anyString());
        verify(orderProducer).processOrder(any(Order.class));
    }

    @Test
    void testCreateOrderFromCart_UserNotFound() throws MessageQueueFailureException {
        when(userRepository.findByUserId("nonexistentUser")).thenReturn(null);

        // Verify that UserNotFoundException is thrown
        assertThrows(UserNotFoundException.class, () -> {
            orderServiceImpl.createOrderFromCart("nonexistentUser");
        });

        // Verify no interactions with other services
        verify(productService, times(0)).decreaseCountFromProductStock(anyMap());
        verify(userService, times(0)).updateUserOrders(any(Order.class));
        verify(orderProducer, times(0)).processOrder(any(Order.class));

    }

}

