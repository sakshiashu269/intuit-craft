package com.craft.manageOrders.user;

import com.craft.manageOrders.order.Order;

public interface UserService {
    User findUser(String userId);
    void updateUserOrders(Order order);
}
