package com.example.kurs.service;

import com.example.kurs.dto.OrderFromCartRequest;
import com.example.kurs.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Long id, Order orderDetails);
    void deleteOrder(Long id);
    Order createOrderFromCart(OrderFromCartRequest orderRequest);
}
