package com.binar.binarfud.service.impl;

import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.model.User;
import com.binar.binarfud.repository.OrderDetailRepository;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOrderProducts_success() {
        User user = new User();
        user.setUsername("testUser");

        Order order = new Order();
        order.setUser(user);

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Product product = new Product();
        product.setProductCode("ProductCode001");
        product.setPrice(10000d);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        order.setOrderDetails(Collections.singletonList(orderDetail));

        when(orderRepository.save(order)).thenReturn(order);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
        when(productRepository.getProductByProductCode(product.getProductCode())).thenReturn(Optional.of(product));

        orderService.orderProducts(order);

        verify(orderDetailRepository, times(1)).save(orderDetail);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testOrderProducts_userNotFound() {
        Order order = new Order();
        order.setUser(null);

        assertThrows(NullPointerException.class, () -> orderService.orderProducts(order));
    }

    @Test
    public void testResolveOrder() {
        String orderId = "testOrderId";
        Order order = new Order();
        order.setId(orderId);
        order.setCompleted(false);

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.getById(orderId)).thenReturn(order);

        Product product = new Product();
        product.setStock(10);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        order.setOrderDetails(Collections.singletonList(orderDetail));

        orderService.resolveOrder(orderId);

        verify(orderRepository, times(1)).save(order);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testResolveOrderNotFound() {
        String orderId = "nonExistentOrderId";
        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderService.resolveOrder(orderId));
    }

    @Test
    public void testResolveOrderAlreadyResolved() {
        String orderId = "testOrderId";
        Order order = new Order();
        order.setId(orderId);
        order.setCompleted(true);

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.getById(orderId)).thenReturn(order);

        assertThrows(IllegalStateException.class, () -> orderService.resolveOrder(orderId));
    }
}
