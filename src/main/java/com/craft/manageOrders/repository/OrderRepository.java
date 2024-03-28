package com.craft.manageOrders.repository;

import com.craft.manageOrders.entity.Order;
import com.craft.manageOrders.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order insert(Order order);
}