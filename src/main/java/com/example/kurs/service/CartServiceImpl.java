package com.example.kurs.service;

import com.example.kurs.model.Cart;
import com.example.kurs.model.CartItem;
import com.example.kurs.model.Product;
import com.example.kurs.model.User;
import com.example.kurs.repository.CartItemRepository;
import com.example.kurs.repository.CartRepository;
import com.example.kurs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart); // Сохраняем корзину в базе данных
    }

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user); // Получаем элементы корзины пользователя
    }


    @Override
    public void addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found")); // Ищем корзину
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found")); // Ищем продукт

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cartRepository.save(cart); // Сохраняем изменения в корзине
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found")); // Ищем элемент корзины
        cartItemRepository.delete(cartItem); // Удаляем элемент корзины
    }

}
