package com.binar.binarfud.controller;

import com.binar.binarfud.dto.ResponseData;
import com.binar.binarfud.exception.ProcessException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.service.impl.MerchantService;
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
    public ResponseEntity<Object> addMerchant(@Valid @RequestBody Merchant merchant) {

        try {
            return ResponseData.statusResponse(merchantService.createMerchant(merchant), HttpStatus.OK, "success add merchant");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<Object> getMerchants() {

        try {
            return ResponseData.statusResponse(merchantService.getMerchants(), HttpStatus.OK, "success get merchants");
        } catch (ProcessException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping("/open")
    public ResponseEntity<Object> getOpenMerchant() {

        try {
            return ResponseData.statusResponse(merchantService.getOpenMerchants(), HttpStatus.OK, "success get open merchant");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @GetMapping("/{page}")
    public ResponseEntity<Object> getMerchantsWithPagination(@PathVariable int page) {

        try {
            return ResponseData.statusResponse(merchantService.getMerchantsWithPagination(page), HttpStatus.OK, "success get merchants");
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @PutMapping("/{merchantCode}")
    public ResponseEntity<Object> updateProduct(@PathVariable String merchantCode, @Valid @RequestBody Merchant merchant) {

        try {
            merchantService.updateMerchantByMerchantCode(merchantCode, merchant);
            return ResponseData.statusResponse(merchantService.getMerchantByMerchantCode(merchantCode), HttpStatus.OK, "success update merchant");
        } catch (ProcessException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }

    }

    @DeleteMapping("/{merchantCode}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String merchantCode) {

        try {
            merchantService.deleteMerchantByMerchantCode(merchantCode);
            return ResponseData.statusResponse(null, HttpStatus.OK, "success delete merchant");
        } catch (ProcessException e) {
            return ResponseData.statusResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseData.internalServerError(e.getMessage());
        }
    }
}
