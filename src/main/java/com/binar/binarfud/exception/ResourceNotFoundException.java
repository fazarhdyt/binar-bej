package com.binar.binarfud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFoundException extends RuntimeException{

    private String fieldResource;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String fieldResource, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s", fieldResource, fieldName, fieldValue));
        this.fieldResource = fieldResource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}