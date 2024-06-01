package com.example.kurs.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderRequest {
    private Long customerId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemRequest> orderItems;
}
