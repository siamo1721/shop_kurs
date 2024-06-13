package com.example.kurs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFromCartRequest {
    private Long cartId;
    private Long customerId;
}
