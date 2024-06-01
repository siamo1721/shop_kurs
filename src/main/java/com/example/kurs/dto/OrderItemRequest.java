package com.example.kurs.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
