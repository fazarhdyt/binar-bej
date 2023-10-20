package com.binar.binarfud.controller;

import com.binar.binarfud.dto.ResponseData;
import com.binar.binarfud.model.Order;
import com.binar.binarfud.service.impl.OrderService;
import com.binar.binarfud.service.impl.ReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/{id}")
    public void getReceipt(@PathVariable String id) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"binarfud-receipt.pdf\"");
        JasperPrint jasperPrint = reportService.generateReceipt(id);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @PostMapping
    public void orderProducts(@Valid @RequestBody Order order) {

        try {
            orderService.orderProducts(order);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"binarfud-receipt.pdf\"");
            JasperPrint jasperPrint = reportService.generateReceipt(order.getId());
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//            return ResponseData.statusResponse(null, HttpStatus.OK, "success order products");
        } catch (Exception e) {
//            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @PostMapping("/resolve")
    public ResponseEntity<Object> resolveOrder(@Valid @RequestBody Order order) {

        try {
            orderService.resolveOrder(order.getId());
            return ResponseData.statusResponse(null, HttpStatus.OK, "success resolve order");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }
}
