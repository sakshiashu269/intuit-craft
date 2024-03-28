package com.craft.manageOrders.controller;

import com.craft.manageOrders.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{productId}/price")
    public double getPriceForProduct(@PathVariable int productId) {
        logger.info("Inside getPriceForProduct method");
        return productService.getPriceQuote(productId);
    }



}
