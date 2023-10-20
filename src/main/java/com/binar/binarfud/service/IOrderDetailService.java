package com.binar.binarfud.service;

import com.binar.binarfud.dto.OrderDetailDto;

import java.util.List;

public interface IOrderDetailService {

    List<OrderDetailDto> getOrderDetail(String orderId);
}
