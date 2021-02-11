package com.example.calcrest.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, IllegalArgumentException.class,ArithmeticException.class})
    public ResponseEntity<Object> handlerApiRequestException(RuntimeException e){
        HttpStatus httpStatus = null;
        if (e.getClass().equals(NotFoundException.class)){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        if (e.getClass().equals(IllegalArgumentException.class) || e.getClass().equals(ArithmeticException.class)){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        ApiException apiException = new ApiException(e.getMessage(), httpStatus);
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }


}
