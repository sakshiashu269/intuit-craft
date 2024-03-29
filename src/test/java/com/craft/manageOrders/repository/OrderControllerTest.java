package com.craft.manageOrders.repository;

import com.craft.manageOrders.order.OrderController;
import com.craft.manageOrders.order.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testPlaceOrder_Success() {
        // Mock data
        String userId = "testUserId";

        // Mock behavior
        when(orderService.createOrderFromCart(userId)).thenReturn(true);

        // Invoke the controller method
        ResponseEntity<String> response = orderController.placeOrder(userId);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Order placed successfully", response.getBody());
    }

    @Test
    public void testPlaceOrder_Failure() {
        // Mock data
        String userId = "testUserId";

        // Mock behavior
        when(orderService.createOrderFromCart(userId)).thenReturn(false);

        // Invoke the controller method
        ResponseEntity<String> response = orderController.placeOrder(userId);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to place order", response.getBody());
    }
}
