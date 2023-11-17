package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.OrderDetailDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDto> getOrderDetail(String orderId) {
        try {
            log.info("trying to get orders detail");
            if (!orderRepository.existsById(orderId)) {
                throw new ResourceNotFoundException("order", "orderId", orderId);
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

    @Override
    @Transactional
    public void deleteOldOrderDetails() {
//        LocalDateTime cutoffDate = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        int aWeekInMillis = 7 * 24 * 60 * 60 * 1000;
        Date cutoffDate = new Date(System.currentTimeMillis() - aWeekInMillis);
        List<Order> oldOrderDetails = orderRepository.findByOrderTimeBefore(cutoffDate);
        oldOrderDetails.forEach(order -> orderDetailRepository.deleteByOrderId(order.getId()));
        orderRepository.deleteInBatch(oldOrderDetails);
    }

    @Override
    @Transactional
    @Scheduled(cron = "59 59 23 * * *")
//    @Scheduled(cron = "* * * * * * ")
    public void scheduledDeleteOldOrderDetails() {
        SimpleDateFormat fmt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        deleteOldOrderDetails();
        log.info("deleting history of order detail older than a week at " + fmt.format(System.currentTimeMillis()));
    }
}
