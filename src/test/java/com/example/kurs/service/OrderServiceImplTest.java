package com.example.kurs.service;

import com.example.kurs.dto.OrderItemRequest;
import com.example.kurs.dto.OrderRequest;
import com.example.kurs.exception.InsufficientStockException;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Customer;
import com.example.kurs.model.Order;
import com.example.kurs.model.Product;
import com.example.kurs.model.User;
import com.example.kurs.repository.CustomerRepository;
import com.example.kurs.repository.OrderRepository;
import com.example.kurs.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Customer customer;
    private Product product;
    private User user;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1099.99);
        product.setQuantity(10);

        user = new User();
        user.setId(1L);
        user.setUsername("manager");
    }

    @Test
    void createOrder_shouldCreateOrderSuccessfully() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(1L);
        orderRequest.setOrderItems(List.of(new OrderItemRequest()));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(orderRequest, user);

        assertNotNull(order);
        assertEquals(customer, order.getCustomer());
        assertEquals(user, order.getUser());
        assertEquals(BigDecimal.valueOf(1099.99), order.getTotalAmount());
        verify(orderRepository).save(order);
    }

    @Test
    void createOrder_whenCustomerNotFound_shouldThrowException() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest, user));
        assertEquals("Customer not found with id 1", exception.getMessage());
    }

    @Test
    void createOrder_whenProductNotFound_shouldThrowException() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(1L);
        orderRequest.setOrderItems(List.of(new OrderItemRequest()));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest, user));
        assertEquals("Product not found with id 1", exception.getMessage());
    }

    @Test
    void createOrder_whenInsufficientStock_shouldThrowException() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(1L);
        orderRequest.setOrderItems(List.of(new OrderItemRequest()));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> orderService.createOrder(orderRequest, user));
        assertEquals("Insufficient stock for product: Laptop, requested: 15, available: 10", exception.getMessage());
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.valueOf(1099.99));
        order.setUser(user);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
        assertEquals(customer, foundOrder.getCustomer());
        assertEquals(BigDecimal.valueOf(1099.99), foundOrder.getTotalAmount());
    }

    @Test
    void getOrderById_whenOrderNotFound_shouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        assertEquals("Order not found with id 1", exception.getMessage());
    }
}
