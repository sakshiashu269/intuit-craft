package com.craft.manageOrders.user;

import com.craft.manageOrders.exceptions.UserNotFoundException;
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
    public void updateUserOrders(String userId, String orderId) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            // Update user orders
            List<String> userOrders = user.getOrders();
            userOrders.add(orderId);
            user.setOrders(userOrders);

            // Update user cart
            user.getCart().emptyCart();

            // Save the updated user entity back to the database
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User Not Found with userID: "+ userId);
        }
    }
}
