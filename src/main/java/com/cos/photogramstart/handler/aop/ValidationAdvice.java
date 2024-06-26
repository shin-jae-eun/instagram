package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //RestController, Service든 모든 것들이 Component를 상속해서 만들어져 있음.
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println(" web api 컨트롤러 ================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg:args){
            if(arg instanceof BindingResult){
                //BindingResult : 유효성검사를 처리할 때 사용되는 객체
                //arg가 BindingResult타입의 인자라면, 유효성 검사가 필요한 메서드라는 뜻!
                System.out.println("유효성검사하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        System.out.println("error.getDefaultMessage() = " + error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사 실패함", errorMap);
                }
            }
        }

        //proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        //profile함수보다는 먼저 실행

        return proceedingJoinPoint.proceed(); //이때 profile함수가 실행됨.
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println(" web컨트롤러 ================");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg:args){
            System.out.println("유효성검사하는 함수입니다.");
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        System.out.println("error.getDefaultMessage() = " + error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed(); //이때 profile함수가 실행됨.
    }
}
