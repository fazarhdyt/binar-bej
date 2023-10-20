package com.binar.binarfud.service.impl;

import com.binar.binarfud.exception.ProcessException;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.OrderDetailId;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public void orderProducts(Order orders) {
        try {
            log.info("trying to order products");
            if (orders.getUser() == null) {
                throw new NullPointerException("user not found, please register first");
            }
            orders.setUser(userRepository.getUserByUsername(orders.getUser().getUsername()).get());
            Order order = orderRepository.save(orders);

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
            log.error("user with username: {} failed ordered products\n", orders.getUser().getUsername() + e.getMessage());
            throw e;
        }
    }

    @Override
    public void resolveOrder(String id) {
        try {
            log.info("trying to resolve order with order id: {}", id);
            if (!orderRepository.existsById(id)) {
                throw new ProcessException("order", "id", id);
            }
            Order order = orderRepository.getById(id);
            if (order.isCompleted()) {
                throw new IllegalStateException("order with order id " + id + " already resolved");
            }
            order.setCompleted(Boolean.TRUE);
            order.getOrderDetails().forEach(orderDetail -> {
                Product product = orderDetail.getProduct();
                int newStock = product.getStock() - orderDetail.getQuantity();
                product.setStock(newStock);
                productRepository.save(product);
            });
            orderRepository.save(order);
            log.info("successfully resolve order with order id: {}", id);
        } catch (Exception e) {
            log.error("resolve order failed\n" + e.getMessage());
            throw e;
        }
    }

}
