package com.example.kurs.repository;

import com.example.kurs.model.CartItem;
import com.example.kurs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}
