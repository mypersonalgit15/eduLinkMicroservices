package com.cts.student_service.application.classException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StudentException extends RuntimeException {
    private final HttpStatus httpStatus;
    public StudentException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
