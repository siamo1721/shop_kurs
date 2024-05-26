package com.example.kurs.service;

import com.example.kurs.dto.OrderRequest;
import com.example.kurs.model.Order;
import com.example.kurs.model.User;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(OrderRequest orderRequest, User user);
    Order updateOrder(Long id, Order orderDetails);
    void deleteOrder(Long id);
}
