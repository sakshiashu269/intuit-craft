package com.craft.manageOrders.entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Integer, Integer> productVsUnits = new HashMap<>();
    int totalPrice;

}
