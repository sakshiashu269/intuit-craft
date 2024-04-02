package com.craft.manageOrders.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateProductStock(String productId, int quantity) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("productId").is(productId)),
                new Update().inc("productStock", quantity),
                Product.class
        );
    }

    @Override
    public void updateProductAvailability(String productId, ProductAvailability productAvailability) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("productId").is(productId)),
                new Update().set("productAvailability", productAvailability),
                Product.class
        );
    }
}

