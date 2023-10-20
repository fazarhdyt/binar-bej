package com.binar.binarfud.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    private Date orderTime;
    private Date completionTime;
    private String destinationAddress;
    private boolean completed;
    private UserDto user;
}
