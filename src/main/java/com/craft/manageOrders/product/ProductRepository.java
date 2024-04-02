package com.craft.manageOrders.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductId(String productId);
    @Query("{'productId': ?0}")
    @Update("{'$set': {'productAvailability': ?1}}")
    void updateProductAvailability(String productId, ProductAvailability productAvailability);

    @Query("{'productId': ?0}")
    @Update("{'$set': {'productStock': ?1}}")
    void updateProductStock(String productId, float quantity);
}
