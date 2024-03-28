package com.craft.manageOrders.service;

import com.craft.manageOrders.controller.PaymentController;
import com.craft.manageOrders.entity.Order;
import com.craft.manageOrders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PaymentService paymentService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.productService = productService;
    }

    @Override
    public Order submitOrder(Order order) {
        Boolean isPaymentSuccess = paymentService.createPayment(order.getBillAmount(), order.getPaymentMode());
        if(isPaymentSuccess){
            updateProductStock(order.getProductVsUnits());
            return orderRepository.insert(order);
        }
        else{
            throw new RuntimeException("Unable to make payment, please try again!");
        }
    }

    private void updateProductStock(Map<Integer, Integer> productVsUnits) {
        for (Map.Entry<Integer, Integer> entry : productVsUnits.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            productService.updateProductStock(productId, quantity);
        }
    }
}
