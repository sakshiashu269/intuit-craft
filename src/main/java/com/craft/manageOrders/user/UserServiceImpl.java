package com.craft.manageOrders.user;

import com.craft.manageOrders.exceptions.UserNotFoundException;
import com.craft.manageOrders.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("Failed to fetch user details " + userId);
        }
        return user;
    }

    @Override
    public void updateUserOrders(Order order) {
        User user = userRepository.findByUserId(order.getUserId());
        if (user != null) {
            // Update user orders
            updateUserOrders(user, order);
            // Update user cart
            updateUserCart(user, order);
        } else {
            throw new UserNotFoundException("User Not Found with userID: "+ order.getUserId());
        }
    }

    private void updateUserCart(User user, Order order) {
        user.getCart().emptyCart();
        userRepository.updateUserCart(user.getUserId(), user.getCart());
    }
    private void updateUserOrders(User user, Order order) {
        List<String> userOrders = user.getOrders();
        userOrders.add(order.getOrderId());
        user.setOrders(userOrders);
        userRepository.updateUserOrders(user.getUserId(), user.getOrders());
        user.setOrders(userOrders);
    }
}
