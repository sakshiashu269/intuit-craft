package com.craft.manageOrders.product;

import java.util.Map;

public interface ProductService {
    public double getPriceQuote(String productId);

    void decreaseCountFromProductStock(Map<String, Integer> productVsUnits);

    void increaseCountInProductStock(Map<String, Integer> productVsUnits);
}
