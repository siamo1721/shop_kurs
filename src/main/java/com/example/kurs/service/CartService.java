package com.example.kurs.service;

import com.example.kurs.model.Cart;
import com.example.kurs.model.CartItem;
import com.example.kurs.model.User;

import java.util.List;

public interface CartService {
    Cart createCart(User user);

    List<CartItem> getCartItems(User user);

    void addToCart(Long cartId, Long productId, int quantity);

    void removeFromCart(Long cartItemId);
}
