package com.example.kurs.service;


import com.example.kurs.dto.OrderFromCartRequest;
import com.example.kurs.exception.InsufficientStockException;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.*;
import com.example.kurs.repository.*;
import com.example.kurs.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderFromCartSuccess() {
        Long cartId = 1L;
        Long customerId = 1L;
        OrderFromCartRequest request = new OrderFromCartRequest();

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        Customer customer = new Customer();
        customer.setId(customerId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        orderService.createOrderFromCart(request);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrderFromCartCartNotFound() {
        Long cartId = 1L;
        Long customerId = 1L;
        OrderFromCartRequest request = new OrderFromCartRequest();

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrderFromCart(request));
    }

    @Test
    void testCreateOrderFromCartCustomerNotFound() {
        Long cartId = 1L;
        Long customerId = 1L;
        OrderFromCartRequest request = new OrderFromCartRequest();

        Cart cart = new Cart();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrderFromCart(request));
    }

    @Test
    void testCreateOrderFromCartInsufficientStock() {
        Long cartId = 1L;
        Long customerId = 1L;
        OrderFromCartRequest request = new OrderFromCartRequest();

        Cart cart = new Cart();
        Customer customer = new Customer();
        Product product = new Product();
        product.setQuantity(1);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cart.getItems().add(cartItem);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        assertThrows(InsufficientStockException.class, () -> orderService.createOrderFromCart(request));
    }
}
