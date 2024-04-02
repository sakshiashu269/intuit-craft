package com.craft.manageOrders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.craft.manageOrders.exceptions.ProductNotFoundException;
import com.craft.manageOrders.product.Product;
import com.craft.manageOrders.product.ProductRepository;
import com.craft.manageOrders.product.ProductService;
import com.craft.manageOrders.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;


    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPriceQuote_ProductFound() {
        String productId = "testProductId";
        double unitPrice = 10.0;
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Test Product");
        product.setUnitPrice(unitPrice);

        when(productRepository.findByProductId(productId)).thenReturn(product);

        double price = productService.getPriceQuote(productId);

        assertEquals(unitPrice, price);
    }

    @Test
    void testGetPriceQuote_ProductNotFound() {
        String productId = "nonexistentProductId";

        when(productRepository.findByProductId(productId)).thenReturn(null);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getPriceQuote(productId);
        });

        assertEquals("Product Not Found", exception.getMessage());
    }
}
