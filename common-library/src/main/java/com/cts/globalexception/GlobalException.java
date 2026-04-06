package com.cts.globalexception;

import com.cts.classexception.StudentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> studentExceptionHandler(StudentException s){
        return ResponseEntity.status(s.getHttpStatus()).body(s.getMessage());
    }
}
