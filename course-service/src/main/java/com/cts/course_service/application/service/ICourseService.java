package com.cts.course_service.application.service;

import com.cts.dto.response.CourseDetailByIdProjection;
import com.cts.course_service.application.projection.CourseDetailProjection;
import com.cts.dto.response.CourseProjection;
import com.cts.dto.request.CourseEnrollmentDto;
import com.cts.dto.request.CourseRegistrationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ICourseService {
    String registerCourse(CourseRegistrationDto courseRegistrationDto);
    void checkCourseExistByCourseId(Long courseId);
    List<CourseProjection> findAllAvailableCourse();
    String findCourseTitleByCourseId(Long courseId);
    String updateCourse(Long courseId, CourseRegistrationDto courseRegistrationDto);
    String patchCourse(Long courseId, Map<String, Object> updates);

    @Transactional
    List<CourseProjection> searchCoursesByName(String courseName);

    String deleteCourse(Long courseId);
    List<CourseProjection> getCoursesByFaculty(Long facultyId);
    int getFacultyCourseCount(Long facultyId );
    CourseDetailByIdProjection findCourseDetailsById(Long courseId);
    String courseEnrollmentRequest(CourseEnrollmentDto courseEnrollmentDto);
    String updateCourseRating(Long courseId, double newCourseRating);
    List<CourseDetailProjection> findCourseListByStudentId(Long studentId);

}