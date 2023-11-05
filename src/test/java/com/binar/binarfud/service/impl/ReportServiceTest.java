package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.*;
import com.binar.binarfud.repository.OrderRepository;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private DataSource dataSource;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateReceipt() throws Exception {
        String orderId = "testOrderId";
        Order order = new Order();
        order.setId(orderId);
        order.setOrderTime(new Date());
        User user = new User();
        user.setUsername("testUser");
        order.setUser(user);
        order.setDestinationAddress("Test Address");

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setTotalPrice(5000d);
        Product product = new Product();
        product.setMerchant(new Merchant());
        orderDetail.setProduct(product);

        order.setOrderDetails(Collections.singletonList(orderDetail));

        when(orderRepository.getById(orderId)).thenReturn(order);
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
        InputStream fileReport = new ClassPathResource("reports/binarfud_receipt_v2.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);

        JasperPrint jasperPrint = reportService.generateReceipt(orderId);

        verify(dataSource, times(1)).getConnection();
        verify(orderRepository, times(1)).getById(orderId);
    }

}
