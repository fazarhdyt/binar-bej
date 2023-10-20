package com.binar.binarfud.dto;

import lombok.Data;

@Data
public class MerchantDto {

    private String merchantCode;
    private String merchantName;
    private String merchantLocation;
    private boolean open;

}
