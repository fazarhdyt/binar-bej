package com.binar.binarfud.service;

import com.binar.binarfud.model.Order;

import java.util.List;

public interface IOrderService {

    List<Order> getOrders();

    void orderProducts(Order order);

}
