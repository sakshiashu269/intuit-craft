package com.craft.manageOrders.service;

import com.craft.manageOrders.controller.ProductController;
import com.craft.manageOrders.entity.Product;
import com.craft.manageOrders.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public double getPriceQuote(int productId) {
        Product product = productRepository.findByProductId(productId);
        if (product != null) {
            logger.info("Product with productId as " + productId + " has price " + product.getUnitPrice());
            return product.getUnitPrice();
        } else {
            throw new RuntimeException("Product Not Found");
        }
    }

    @Override
    public void updateProductStock(int productId, int quantity) {

    }
}
