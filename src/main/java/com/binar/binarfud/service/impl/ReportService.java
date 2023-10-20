package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Order;
import com.binar.binarfud.model.OrderDetail;
import com.binar.binarfud.repository.OrderRepository;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OrderRepository orderRepository;

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JasperPrint generateReceipt(String orderId) throws Exception {
        InputStream fileReport = new ClassPathResource("reports/binarfud_receipt_v2.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        Map<String, Object> params = new HashMap<>();
        Order order = orderRepository.getById(orderId);
        Double total = order.getOrderDetails().stream()
                .mapToDouble(OrderDetail::getTotalPrice).sum();
        params.put("order_id", orderId);
        params.put("order_time", order.getOrderTime());
        params.put("username", order.getUser().getUsername());
        params.put("destination_address", order.getDestinationAddress());
        params.put("total", total);
        params.put("merchant", order.getOrderDetails().get(0).getProduct().getMerchant().getMerchantName());
        params.put("merchant_location", order.getOrderDetails().get(0).getProduct().getMerchant().getMerchantLocation());
        return JasperFillManager.fillReport(jasperReport, params, getConnection());
    }
}
