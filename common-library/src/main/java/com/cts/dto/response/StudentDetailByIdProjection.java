package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentDetailByIdProjection {
    private Long studentId;
    private LocalDate studentDOB;
    private String studentGender;
    private String studentAddress;
    private LocalDateTime studentEnrollmentDateTime;
}
