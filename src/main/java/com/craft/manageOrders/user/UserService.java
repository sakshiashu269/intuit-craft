package com.craft.manageOrders.user;

public interface UserService {
    User findUser(String userId);
    void updateUserOrders(String orderId, String id);
}
