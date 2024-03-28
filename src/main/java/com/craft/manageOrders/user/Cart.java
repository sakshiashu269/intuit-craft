package com.craft.manageOrders.user;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, Integer> productVsUnits = new HashMap<>();
    private int totalPrice;

    public Map<String, Integer> getProductVsUnits() {
        return productVsUnits;
    }

    public void setProductVsUnits(Map<String, Integer> productVsUnits) {
        this.productVsUnits = productVsUnits;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void emptyCart() {
        productVsUnits.clear();
        totalPrice = 0;
    }
}
