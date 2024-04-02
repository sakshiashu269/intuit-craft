package com.craft.manageOrders.product;

import com.craft.manageOrders.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{productId}/price")
    @Cacheable(value = "productPrices", key = "#productId")
    public ResponseEntity<Double> getPriceForProduct(@PathVariable String productId) {
        try {
            double price = productService.getPriceQuote(productId);
            logger.info("Price fetched for product with ID {}: {}", productId, price);
            return ResponseEntity.ok(price);
        } catch (ProductNotFoundException ex) {
            logger.error("Product with ID {} not found", productId, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("Failed to fetch price for product with ID {}", productId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
