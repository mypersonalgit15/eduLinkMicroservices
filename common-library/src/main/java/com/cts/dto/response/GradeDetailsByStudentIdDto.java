package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GradeDetailsByStudentIdDto {
    private Long courseId;
    private double score;
    private String grade;
}
