package com.craft.manageOrders.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{productId}/price")
    @Cacheable("productPrices")
    public double getPriceForProduct(@PathVariable String productId) {
        return productService.getPriceQuote(productId);
    }



}
