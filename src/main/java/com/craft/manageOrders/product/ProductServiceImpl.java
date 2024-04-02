package com.craft.manageOrders.product;

import com.craft.manageOrders.exceptions.CartEmptyException;
import com.craft.manageOrders.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
//@Cacheable(value = "productPrices", key = "#productId")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public double getPriceQuote(String productId) {
        Product product = productRepository.findByProductId(productId);
        if (product != null) {
            logger.info("Product with productId as " + productId + " has price " + product.getUnitPrice());
            return product.getUnitPrice();
        } else {
            logger.error("Product with productId as " + productId + " was not found");
            throw new ProductNotFoundException("Product Not Found");
        }
    }

    public void decreaseCountFromProductStock(Map<String, Integer> productVsUnits){
        if (productVsUnits.isEmpty()) {
            throw new CartEmptyException("Cart Empty, Please select Products");
        }
        for (Map.Entry<String, Integer> entry : productVsUnits.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            float currentQuantity = product.getProductStock();
            if (product == null || currentQuantity < quantity) {
                logger.error("Product is not available");
                throw new ProductNotFoundException("Product not Available " + product);
            }
            if(currentQuantity <= 0 || currentQuantity-quantity<=0){
                productRepository.updateProductAvailability(productId, ProductAvailability.OUT_OF_STOCK);
            }
            try {
                productRepository.updateProductStock(productId, currentQuantity-quantity);
            } catch (Exception e) {
                // Handle the exception (e.g., log an error message, throw a custom exception)
                // For example:
                logger.error("Failed to update product stock for productId: " + productId, e);
                throw new RuntimeException("Failed to update product stock", e);
            }
        }
    }

    public void increaseCountInProductStock(Map<String, Integer> productVsUnits){
        for (Map.Entry<String, Integer> entry : productVsUnits.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            if (product == null) {
                logger.error("Product with productId as " + productId + " was not found");
                throw new ProductNotFoundException("Product Not Found");
            }
            if(product.getProductStock() > 0){
                productRepository.updateProductAvailability(productId, ProductAvailability.IN_STOCK);
            }
            productRepository.updateProductStock(productId, quantity);
        }
    }
}
