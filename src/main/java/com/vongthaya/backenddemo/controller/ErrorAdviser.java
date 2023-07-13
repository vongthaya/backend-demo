package com.vongthaya.backenddemo.controller;

import com.vongthaya.backenddemo.exception.BaseException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorAdviser {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exc) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        response.setMessage(exc.getMessage());

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleBaseException(Exception exc) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        response.setMessage(exc.getMessage());

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @Data
    private class ErrorResponse {

        private Date timestamp = new Date();

        private int status;

        private String message;

    }

}
