package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentGradeProjection {
    private double score;
    private String grade;
}
