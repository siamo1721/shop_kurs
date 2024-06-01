package com.example.kurs.service;

import com.example.kurs.dto.OrderItemRequest;
import com.example.kurs.dto.OrderRequest;
import com.example.kurs.exception.InsufficientStockException;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.*;
import com.example.kurs.repository.CustomerRepository;
import com.example.kurs.repository.OrderRepository;
import com.example.kurs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });
    }

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) {
        log.info("Creating order for user: {}", user.getUsername());
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", orderRequest.getCustomerId());
                    return new ResourceNotFoundException("Customer not found with id " + orderRequest.getCustomerId());
                });

        Order order = new Order();
        order.setCustomer(customer);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> {
                        log.error("Product not found with id: {}", itemRequest.getProductId());
                        return new ResourceNotFoundException("Product not found with id " + itemRequest.getProductId());
                    });

            if (product.getQuantity() < itemRequest.getQuantity()) {
                log.error("Insufficient stock for product: {}, requested: {}, available: {}", product.getName(), itemRequest.getQuantity(), product.getQuantity());
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            BigDecimal itemTotal = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created with id: {} for user: {}", savedOrder.getId(), user.getUsername());
        return savedOrder;
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        log.info("Updating order with id: {}", id);
        Order order = getOrderById(id);
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setOrderDate(orderDetails.getOrderDate());
        order.setCustomer(orderDetails.getCustomer());
        order.setOrderItems(orderDetails.getOrderItems());
        Order updatedOrder = orderRepository.save(order);
        log.info("Order with id: {} updated", id);
        return updatedOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        Order order = getOrderById(id);
        orderRepository.delete(order);
        log.info("Order with id: {} deleted", id);
    }
}
