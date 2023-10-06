package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.*;
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
public class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        orderDetailRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testOrderProducts_success() {

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

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        Order order = new Order();
        order.setOrderTime(new Date());
        order.setDestinationAddress("Kelapa Dua");
        order.setUser(user);
        order.setCompleted(false);
        order.setOrderDetails(orderDetails);

        // Act
        orderService.orderProducts(order);

        // Assert
        assertNotNull(order.getId());

        List<Order> ordersInDatabase = orderRepository.findAll();
        assertEquals(1, ordersInDatabase.size());

        List<OrderDetail> orderDetailsInDatabase = orderDetailRepository.findAll();
        assertEquals(1, orderDetailsInDatabase.size());

        OrderDetail orderDetailInDatabase = orderDetailsInDatabase.get(0);
        assertEquals(order.getId(), orderDetailInDatabase.getOrder().getId());
        assertEquals(product.getProductCode(), orderDetailInDatabase.getProduct().getProductCode());
        assertEquals(2, orderDetailInDatabase.getQuantity());
        assertEquals(10000, orderDetailInDatabase.getTotalPrice());
    }

    @Test
    public void testOrderProducts_userNotRegister() {

        Product product = new Product();
        product.setProductCode("KSUSU");
        product.setProductName("Ultra Milk");
        product.setPrice(10000);

        productRepository.save(product);

        Order order = new Order();
        order.setOrderTime(new Date());
        order.setDestinationAddress("Sukmajaya");
        order.setCompleted(false);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        order.setOrderDetails(orderDetails);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> orderService.orderProducts(order));
    }

    @Test
    public void testGetOrders_nonEmptyList() {

        Order order1 = new Order();
        Order order2 = new Order();
        orderRepository.saveAll(Arrays.asList(order1, order2));

        // Act
        List<Order> result = orderService.getOrders();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOrders_emptyList() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderService.getOrders());
    }
}
