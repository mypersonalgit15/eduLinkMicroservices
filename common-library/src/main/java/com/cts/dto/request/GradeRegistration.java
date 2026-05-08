package com.cts.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeRegistration {
    @NotNull(message = "Student ID cannot be null")
    private Long studentId;
    @NotNull(message = "Course ID cannot be null")
    private Long courseId;
}
