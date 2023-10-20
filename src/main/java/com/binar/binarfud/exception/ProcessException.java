package com.binar.binarfud.exception;

import lombok.Data;

@Data
public class ProcessException extends RuntimeException{

    private String fieldResource;
    private String fieldName;
    private Object fieldValue;

    public ProcessException(String fieldResource, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s", fieldResource, fieldName, fieldValue));
        this.fieldResource = fieldResource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
