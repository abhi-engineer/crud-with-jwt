package com.crud.utility;

import com.crud.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private Environment environmentVariable;

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> myAppException(MyException exception){
        String msg = environmentVariable.getProperty(exception.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
