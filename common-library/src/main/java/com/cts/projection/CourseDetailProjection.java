package com.cts.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseDetailProjection {
    private String courseTitle;
    private String courseGradeLevel;
    private double courseRating;
}
