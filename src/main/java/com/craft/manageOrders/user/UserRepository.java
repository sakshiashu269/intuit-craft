package com.craft.manageOrders.user;

import com.craft.manageOrders.cart.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserId(String userId);
    @Query("{'userId': ?0}")
    @Update("{'$set': {'user': ?1}}")
    void updateUser(String userId, User user);

    @Query("{'userId': ?0}")
    @Update("{'$set': {'orders': ?1}}")
    void updateUserOrders(String userId, List<String> userOrders);

    @Query("{'userId': ?0}")
    @Update("{'$set': {'cart': ?1}}")
    void updateUserCart(String userId, Cart cart);
}