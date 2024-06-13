package com.example.kurs.service;

import com.example.kurs.model.CartItem;
import com.example.kurs.model.Product;
import com.example.kurs.model.User;
import com.example.kurs.repository.CartRepository;
import com.example.kurs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void addToCart(Long productId, int quantity, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = new CartItem(null, product, quantity, user);
        cartRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long cartItemId, User user) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        if (!cartItem.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized action");
        }
        cartRepository.delete(cartItem);
    }
}
