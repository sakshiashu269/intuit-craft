package com.craft.manageOrders.order;

import com.craft.manageOrders.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order insert(Order order);
}