package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{
    //객체 구분할 때!
    private static final long serialVaersionUID = 1L;

    private Map<String, String> errorMap;
    public CustomValidationApiException(String message, Map<String, String> errorMap){
        super(message);
        this.errorMap = errorMap;
    }

    public CustomValidationApiException(String message){
        super(message);
    }
    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}
