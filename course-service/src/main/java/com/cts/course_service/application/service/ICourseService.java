package com.cts.course_service.application.service;

import com.cts.course_service.application.projection.CourseProjection;
import com.cts.dto.request.CourseRegistrationDto;

import java.util.List;
import java.util.Map;

public interface ICourseService {
    String registerCourse(CourseRegistrationDto courseRegistrationDto);
    String updateCourse(Long courseId, CourseRegistrationDto courseRegistrationDto);
    String patchCourse(Long courseId, Map<String, Object> updates);
    String deleteCourse(Long courseId);
    List<CourseProjection> getCoursesByFaculty(Long facultyId);
    public int getFacultyCourseCount(Long facultyId );
}