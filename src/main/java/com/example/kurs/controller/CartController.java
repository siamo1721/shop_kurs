package com.example.kurs.controller;

import com.example.kurs.model.Cart;
import com.example.kurs.model.CartItem;
import com.example.kurs.model.User;
import com.example.kurs.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCartItems(@AuthenticationPrincipal User user) {
        return cartService.getCartItems(user);
    }

    @PostMapping
    public void addToCart(@RequestBody Map<String, Object> payload, @AuthenticationPrincipal User user) {
        Long cartId = ((Number) payload.get("cartId")).longValue();
        Long productId = ((Number) payload.get("productId")).longValue();
        int quantity = (Integer) payload.get("quantity");
        cartService.addToCart(cartId, productId, quantity);
    }

    @DeleteMapping("/{cartItemId}")
    public void removeFromCart(@PathVariable Long cartItemId, @AuthenticationPrincipal User user) {
        cartService.removeFromCart(cartItemId);
    }

    @PostMapping("/create")
    public Cart createCart(@AuthenticationPrincipal User user) {
        return cartService.createCart(user);
    }
}
