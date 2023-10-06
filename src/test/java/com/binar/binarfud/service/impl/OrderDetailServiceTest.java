package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.*;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderDetailServiceTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        orderDetailRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testGetOrderDetail_NonEmptyList() {

        User user = User.builder()
                .username("user")
                .email("user@gmail.com")
                .password("password")
                .build();

        userRepository.save(user);

        Product product = new Product();
        product.setProductCode("KSUSU");
        product.setProductName("Ultra Milk");
        product.setPrice(5000);
        productRepository.save(product);

        Product product2 = new Product();
        product2.setProductCode("YKLT");
        product2.setProductName("Yakult");
        product2.setPrice(2000);
        productRepository.save(product2);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(product2);
        orderDetail2.setQuantity(4);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails = Arrays.asList(orderDetail, orderDetail2);

        Order order = new Order();
        order.setOrderTime(new Date());
        order.setDestinationAddress("Kelapa Dua");
        order.setUser(user);
        order.setCompleted(false);
        order.setOrderDetails(orderDetails);

        // Act
        orderService.orderProducts(order);
        List<OrderDetail> result = orderDetailService.getOrderDetail();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOrderDetail_EmptyList() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderDetailService.getOrderDetail());
    }
}
