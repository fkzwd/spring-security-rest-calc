package com.example.calcrest.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException {

    private final String massage;
    private final HttpStatus httpStatus;


    public ApiException(String massage, HttpStatus httpStatus) {
        this.massage = massage;
        this.httpStatus = httpStatus;
    }

    public String getMassage() {
        return massage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
