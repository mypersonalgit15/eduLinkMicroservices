package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseProjection {
    private Long courseId;
    private String courseTitle;
    private String courseSubject;
    private String courseGradeLevel;
    private Integer courseCredit;
    private String courseDescription;
    private String courseStatus;
    private Double courseRating;

}
