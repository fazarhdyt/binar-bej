package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.OrderDetailDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.OrderRepository;
import com.binar.binarfud.service.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderDetailServiceTest {

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderDetail_success() {
        String orderId = "testOrderId";
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Product product = new Product();
        product.setProductCode("ProductCode001");
        product.setPrice(10000d);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);

        order.setOrderDetails(Collections.singletonList(orderDetail));

        List<OrderDetailDto> result = orderDetailService.getOrderDetail(orderId);

        verify(orderRepository, times(1)).findById(orderId);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), EntityMapper.orderDetailToOrderDto(orderDetail));
    }

    @Test
    public void testGetOrderDetailNotFound() {
        String orderId = "nonExistentOrderId";
        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderDetailService.getOrderDetail(orderId));
    }
}
