package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.OrderDetailDto;
import com.binar.binarfud.exception.ProcessException;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDetailDto> getOrderDetail(String orderId) {
        try {
            log.info("trying to get orders detail");
            if (!orderRepository.existsById(orderId)) {
                throw new ProcessException("order", "orderId", orderId);
            }
            Order order = orderRepository.findById(orderId).get();
            List<OrderDetailDto> orderDetailsDto = new ArrayList<>();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                OrderDetailDto orderDetailDto = EntityMapper.orderDetailToOrderDto(orderDetail);
                orderDetailsDto.add(orderDetailDto);
            }
            log.info("get orders detail successfully");
            return orderDetailsDto;
        } catch (Exception e) {
            log.error("get orders detail failed\n" + e.getMessage());
            throw e;
        }
    }
}
