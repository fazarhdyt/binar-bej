package com.binar.binarfud.service;

import com.binar.binarfud.model.Order;

public interface IOrderService {

    void orderProducts(Order order);

    void resolveOrder(String id);

}
