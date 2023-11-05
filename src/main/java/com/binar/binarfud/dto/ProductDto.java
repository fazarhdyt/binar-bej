package com.binar.binarfud.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String productCode;
    private String productName;
    private Integer stock;
    private Double price;
    private MerchantDto merchant;
}
