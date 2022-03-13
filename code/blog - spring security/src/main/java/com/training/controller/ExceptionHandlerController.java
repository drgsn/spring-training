package com.training.controller;

import com.training.exception.ServerError;
import com.training.exception.TestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({ServerError.class, Exception.class})
    public String serverError(){
        return "server-error";
    }

    @ExceptionHandler(TestException.class)
    public ResponseEntity testException(){
        return new ResponseEntity<>("Test Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
