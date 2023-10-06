package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.OrderDetailId;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void orderProducts(Order order) {
        try {
            log.info("trying to order products");
            if (order.getUser() == null) {
                throw new NullPointerException("user not found, please register first");
            }
            orderRepository.save(order);

            List<OrderDetail> listOrderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : listOrderDetails) {
                OrderDetailId id = new OrderDetailId(order.getId(),
                        orderDetail.getProduct().getProductCode());
                Product product = productRepository.getProductByProductCode(orderDetail.getProduct().getProductCode()).get();
                orderDetail.setId(id);
                orderDetail.setProduct(product);
                orderDetail.setOrder(order);
                orderDetail.setQuantity(orderDetail.getQuantity());
                orderDetail
                        .setTotalPrice(product.getPrice() * orderDetail.getQuantity());
                orderDetailRepository.save(orderDetail);
            }
            log.info("user with username: {} successfully ordered products", order.getUser().getUsername());
        } catch (Exception e) {
            e.getMessage();
            log.error("user with username: {} failed ordered products", order.getUser().getUsername());
            throw e;
        }
    }

    @Override
    public List<Order> getOrders() {
        try {
            log.info("trying to get all orders");
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                throw new RuntimeException("order is empty");
            }
            log.info("get all orders successfully");
            return orders;
        } catch (Exception e) {
            e.getMessage();
            log.error("get orders failed");
            throw e;
        }
    }
}
