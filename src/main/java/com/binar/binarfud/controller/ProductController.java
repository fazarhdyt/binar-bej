package com.binar.binarfud.controller;

import com.binar.binarfud.dto.ResponseData;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.service.impl.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "api to add product")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product) {

        try {
            return ResponseData.statusResponse(productService.createProduct(product, product.getMerchant().getMerchantCode()), HttpStatus.OK, "success add product");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping()
    @Operation(summary = "api to get products")
    public ResponseEntity<Object> getProducts() {

        try {
            return ResponseData.statusResponse(productService.getProducts(), HttpStatus.OK, "success get products");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping("/{page}")
    @Operation(summary = "api to get products per page")
    public ResponseEntity<Object> getProductsWithPagination(@PathVariable int page) {

        try {
            return ResponseData.statusResponse(productService.getProductsWithPagination(page), HttpStatus.OK, "success get products");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @PutMapping("/{productCode}")
    @Operation(summary = "api to update product by product code")
    public ResponseEntity<Object> updateProduct(@PathVariable String productCode, @Valid @RequestBody Product product) {

        try {
            productService.updateProductByProductCode(productCode, product);
            return ResponseData.statusResponse(productService.getProductByProductCode(productCode), HttpStatus.OK, "success update product");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @DeleteMapping("/{productCode}")
    @Operation(summary = "api to delete product by product code")
    public ResponseEntity<Object> deleteProduct(@PathVariable String productCode) {

        try {
            productService.deleteProductByProductCode(productCode);
            return ResponseData.statusResponse(null, HttpStatus.OK, "success delete product");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }
    }

}