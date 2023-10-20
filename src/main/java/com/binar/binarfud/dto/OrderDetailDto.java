package com.binar.binarfud.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    
    private OrderDto order;
    private ProductDto product;
    private Integer quantity;
    private double totalPrice;
}
