package com.craft.manageOrders.product;

import com.craft.manageOrders.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Cacheable(value = "productPrices", key = "#productId")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
    }

    @Override
    @Cacheable("productPrices")
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
        for (Map.Entry<String, Integer> entry : productVsUnits.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            if (product == null || product.getProductStock() < quantity) {
                logger.error("Product is not available");
                throw new ProductNotFoundException("Product not Available " + product);
            }
            if(product.getProductStock() <= 0){
                productRepositoryCustom.updateProductAvailability(productId, ProductAvailability.OUT_OF_STOCK);
            }
            productRepositoryCustom.updateProductStock(productId, -quantity);
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
                productRepositoryCustom.updateProductAvailability(productId, ProductAvailability.IN_STOCK);
            }
            productRepositoryCustom.updateProductStock(productId, quantity);
        }
    }
}
