package com.craft.manageOrders.product;

import com.craft.manageOrders.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public double getPriceQuote(String productId) {
        Product product = productRepository.findByProductId(productId);
        if (product != null) {
            logger.info("Product with productId as " + productId + " has price " + product.getUnitPrice());
            return product.getUnitPrice();
        } else {
            throw new ProductNotFoundException("Product Not Found");
        }
    }

    public boolean decreaseCountFromProductStock(Map<String, Integer> productVsUnits){
        for (Map.Entry<String, Integer> entry : productVsUnits.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            if (product == null || product.getProductStock() < quantity) {
                throw new ProductNotFoundException("Product not Available " + product);
            }
            // Update product stock
            product.setProductStock(product.getProductStock() - quantity);
            if(product.getProductStock() <= 0){
                product.setProductAvailability(ProductAvailability.OUT_OF_STOCK);
                mongoTemplate.updateFirst(
                        Query.query(Criteria.where("productId").is(productId)),
                        new Update().set("productAvailability", ProductAvailability.OUT_OF_STOCK),
                        Product.class
                );
            }
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("productId").is(productId)),
                    new Update().inc("productStock", -quantity),
                    Product.class
            );
        }
        return true;
    }

    public void increaseCountInProductStock(Map<String, Integer> productVsUnits){
        for (Map.Entry<String, Integer> entry : productVsUnits.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            if (product == null) {
                return; // Product not found or insufficient stock
            }
            // Update product stock
            product.setProductStock(product.getProductStock() + quantity);
            if(product.getProductStock() > 0){
                product.setProductAvailability(ProductAvailability.IN_STOCK);
                mongoTemplate.updateFirst(
                        Query.query(Criteria.where("productId").is(productId)),
                        new Update().set("productAvailability", ProductAvailability.IN_STOCK),
                        Product.class
                );
            }
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("productId").is(productId)),
                    new Update().inc("productStock", quantity),
                    Product.class
            );
        }
    }
}
