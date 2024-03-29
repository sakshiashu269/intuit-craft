package com.craft.manageOrders.repository;

import com.craft.manageOrders.OrderManagementApplicationTests;
import com.craft.manageOrders.product.Product;
import com.craft.manageOrders.product.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderManagementApplicationTests.class)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void ProductRepository_FindByProductId_ReturnsProductPrice() {
        // Arrange
        Product expectedProduct = createSampleProduct();

        // Act
        Product actualProduct = productRepository.findByProductId("1");

        // Assert
        Assert.assertNotNull(actualProduct);
        Assert.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testFindByProductId_NonExistentProduct_ReturnsNull() {
        // Act
        Product actualProduct = productRepository.findByProductId("nonExistentId");

        // Assert
        Assert.assertNull(actualProduct);
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sample Product");
        product.setUnitPrice(99.99);
        // Set other properties as needed
        return product;
    }
}

