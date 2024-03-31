package com.craft.manageOrders.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom{
    void updateProductStock(String productId, int quantity);
    void updateProductAvailability(String productId, ProductAvailability productAvailability);
}
