package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getOrderDetail() {
        try {
            log.info("trying to get all orders detail");
            List<OrderDetail> orderDetails = orderDetailRepository.findAll();
            if (orderDetails.isEmpty()) {
                throw new RuntimeException("order detail is empty");
            }
            log.info("get all orders detail successfully");
            return orderDetails;
        } catch (Exception e) {
            e.getMessage();
            log.error("get orders detail failed");
            throw e;
        }
    }
}
