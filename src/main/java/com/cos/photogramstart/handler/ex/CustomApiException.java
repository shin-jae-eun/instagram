package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{
    //객체 구분할 때!
    private static final long serialVaersionUID = 1L;

    private Map<String, String> errorMap;


    public CustomApiException(String message){
        super(message);
    }
    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}
