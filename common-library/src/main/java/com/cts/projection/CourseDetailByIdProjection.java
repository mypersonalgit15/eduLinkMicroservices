package com.cts.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseDetailByIdProjection {
    private Long courseId;
    private String courseTitle;
    private String courseSubject;
    private String courseGradeLevel;
    private int courseCredit;
    private String courseStatus;
    private double courseRating;
    private String facultyName;
    private double facultyRating;
}
