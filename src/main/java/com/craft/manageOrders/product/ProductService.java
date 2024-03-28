package com.craft.manageOrders.product;

import java.util.Map;

public interface ProductService {
    public double getPriceQuote(String productId);

    boolean decreaseCountFromProductStock(Map<String, Integer> productVsUnits);

    void increaseCountInProductStock(Map<String, Integer> productVsUnits);
}
