package com.cts.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseAttendanceProjection {
    private Long courseId;
    private String courseTitle;
    private double attendancePercentage;
    private LocalDateTime lastAttendanceDate;
}
