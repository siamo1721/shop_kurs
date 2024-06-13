package com.example.kurs.service;

import com.example.kurs.model.Cart;
import com.example.kurs.model.CartItem;
import com.example.kurs.model.Product;
import com.example.kurs.repository.CartItemRepository;
import com.example.kurs.repository.CartRepository;
import com.example.kurs.repository.ProductRepository;
import com.example.kurs.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCartSuccess() {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;

        Cart cart = new Cart();
        Product product = new Product();
        product.setId(productId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        cartService.addToCart(cartId, productId, quantity);

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddToCartCartNotFound() {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.addToCart(cartId, productId, quantity));
    }

    @Test
    void testAddToCartProductNotFound() {
        Long cartId = 1L;
        Long productId = 1L;
        int quantity = 2;

        Cart cart = new Cart();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.addToCart(cartId, productId, quantity));
    }
}

