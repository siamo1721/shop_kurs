package com.example.kurs.service;

import com.example.kurs.model.CartItem;
import com.example.kurs.model.User;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(User user);
    void addToCart(Long productId, int quantity, User user);
    void removeFromCart(Long cartItemId, User user);
}
