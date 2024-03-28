package com.craft.manageOrders.service;

public interface ProductService {
    public double getPriceQuote(int productId);
    void updateProductStock(int productId, int quantity);
}
