package com.cts.engagement_service.application.GlobalException;

import com.cts.engagement_service.application.classexception.AttendanceException;
import com.cts.engagement_service.application.classexception.FeedbackException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(FeedbackException.class)
    public ResponseEntity<String> feedbackExceptionHandler(FeedbackException s){
        return ResponseEntity.status(s.getHttpStatus()).body(s.getMessage());
    }
    @ExceptionHandler(AttendanceException.class)
    public ResponseEntity<String> AttendanceExceptionHandler(AttendanceException s){
        return ResponseEntity.status(s.getHttpStatus()).body(s.getMessage());
    }
}
