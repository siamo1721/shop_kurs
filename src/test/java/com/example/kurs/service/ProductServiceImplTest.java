package com.example.kurs.service;

import com.example.kurs.exception.InsufficientStockException;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Product;
import com.example.kurs.repository.ProductRepository;
import com.example.kurs.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReduceProductQuantitySuccess() {
        Long productId = 1L;
        int quantity = 2;

        Product product = new Product();
        product.setQuantity(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.reduceProductQuantity(productId, quantity);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testReduceProductQuantityProductNotFound() {
        Long productId = 1L;
        int quantity = 2;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.reduceProductQuantity(productId, quantity));
    }

    @Test
    void testReduceProductQuantityInsufficientStock() {
        Long productId = 1L;
        int quantity = 10;

        Product product = new Product();
        product.setQuantity(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> productService.reduceProductQuantity(productId, quantity));
    }
}

