package com.craft.manageOrders.user;

import com.craft.manageOrders.utility.Address;
import com.craft.manageOrders.cart.Cart;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "User")
public class User {
    private String userId;
    private String email;
    private String userName;
    private UserType userType;
    private Address address;
    private Cart cart;
    private List<String> orders;
    public User() {
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public User(String userId, String email, String userName, UserType userType, Address address, Cart cart, List<String> orders) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.userType = userType;
        this.address = address;
        this.cart = cart;
        this.orders = orders;
    }
}
