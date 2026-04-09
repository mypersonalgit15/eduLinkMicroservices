package com.cts.course_service.application.service;

import com.cts.dto.response.CourseDetailByIdProjection;
import com.cts.course_service.application.projection.CourseDetailProjection;
import com.cts.course_service.application.projection.CourseProjection;
import com.cts.dto.request.CourseEnrollmentDto;
import com.cts.dto.request.CourseRegistrationDto;

import java.util.List;
import java.util.Map;

public interface ICourseService {

    String updateCourse(Long courseId, CourseRegistrationDto courseRegistrationDto);
    String patchCourse(Long courseId, Map<String, Object> updates);
    String deleteCourse(Long courseId);

    CourseDetailByIdProjection findCourseDetailsById(Long courseId);
    String courseEnrollmentRequest(CourseEnrollmentDto courseEnrollmentDto);
    List<CourseDetailProjection> findCourseListByStudentId(Long studentId);

}