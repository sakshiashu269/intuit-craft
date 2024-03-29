package com.craft.manageOrders.user;

import com.craft.manageOrders.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "User")
public class User {
    private String userId;
    private String userName;
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

    public User(String userId, String userName, Address address, Cart cart, List<String> orders) {
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.cart = cart;
        this.orders = orders;
    }
}
