package com.cts.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseProjection {
    private String courseTitle;
    private String courseSubject;
    private String courseGradeLevel;
    private int courseCredit;
    private String courseStatus;
}