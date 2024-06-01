package com.example.kurs.service;

import com.example.kurs.model.CartItem;
import com.example.kurs.model.Product;
import com.example.kurs.model.User;
import com.example.kurs.repository.CartRepository;
import com.example.kurs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItems(User user) {
        log.info("Fetching cart items for user: {}", user.getUsername());
        return cartRepository.findByUser(user);
    }

    @Override
    public void addToCart(Long productId, int quantity, User user) {
        log.info("Adding product with id {} to cart for user: {}", productId, user.getUsername());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new RuntimeException("Product not found");
                });
        CartItem cartItem = new CartItem(null, product, quantity, user);
        cartRepository.save(cartItem);
        log.info("Product with id {} added to cart for user: {}", productId, user.getUsername());
    }

    @Override
    public void removeFromCart(Long cartItemId, User user) {
        log.info("Removing cart item with id {} for user: {}", cartItemId, user.getUsername());
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    log.error("CartItem not found with id: {}", cartItemId);
                    return new RuntimeException("CartItem not found");
                });
        if (!cartItem.getUser().equals(user)) {
            log.error("Unauthorized action by user: {} for cart item id: {}", user.getUsername(), cartItemId);
            throw new RuntimeException("Unauthorized action");
        }
        cartRepository.delete(cartItem);
        log.info("Cart item with id {} removed for user: {}", cartItemId, user.getUsername());
    }
}
