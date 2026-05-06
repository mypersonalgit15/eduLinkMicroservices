package com.cts.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDetailByIdDto {
    private String studentName;
    private String studentEmail;
    private Long studentNumber;
    private Long studentId;
    private LocalDate studentDOB;
    private String studentGender;
    private String studentAddress;
    private LocalDateTime studentEnrollmentDateTime;
}
