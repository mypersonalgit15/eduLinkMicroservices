package com.cts.student_service.application.GlobalException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> studentExceptionHandler(StudentException s){
        return ResponseEntity.status(s.getHttpStatus()).body(s.getMessage());
    }
}
