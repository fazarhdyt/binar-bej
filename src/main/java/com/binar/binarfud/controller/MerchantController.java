package com.binar.binarfud.controller;

import com.binar.binarfud.dto.ResponseData;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.service.impl.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping
    @Operation(summary = "api to create merchant")
    public ResponseEntity<Object> addMerchant(@Valid @RequestBody Merchant merchant) {

        try {
            return ResponseData.statusResponse(merchantService.createMerchant(merchant), HttpStatus.OK, "success add merchant");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping()
    @Operation(summary = "api to get merchants")
    public ResponseEntity<Object> getMerchants() {

        try {
            return ResponseData.statusResponse(merchantService.getMerchants(), HttpStatus.OK, "success get merchants");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping("/open")
    @Operation(summary = "api to get open merchants")
    public ResponseEntity<Object> getOpenMerchant() {

        try {
            return ResponseData.statusResponse(merchantService.getOpenMerchants(), HttpStatus.OK, "success get open merchant");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping("/{page}")
    @Operation(summary = "api to get merchants per page")
    public ResponseEntity<Object> getMerchantsWithPagination(@PathVariable int page) {

        try {
            return ResponseData.statusResponse(merchantService.getMerchantsWithPagination(page), HttpStatus.OK, "success get merchants");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @PutMapping("/{merchantCode}")
    @Operation(summary = "api to update merchant by merchant code")
    public ResponseEntity<Object> updateProduct(@PathVariable String merchantCode, @Valid @RequestBody Merchant merchant) {

        try {
            merchantService.updateMerchantByMerchantCode(merchantCode, merchant);
            return ResponseData.statusResponse(merchantService.getMerchantByMerchantCode(merchantCode), HttpStatus.OK, "success update merchant");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @DeleteMapping("/{merchantCode}")
    @Operation(summary = "api to delete merchant by merchant code")
    public ResponseEntity<Object> deleteProduct(@PathVariable String merchantCode) {

        try {
            merchantService.deleteMerchantByMerchantCode(merchantCode);
            return ResponseData.statusResponse(null, HttpStatus.OK, "success delete merchant");
        } catch (ResourceNotFoundException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }
    }
}
