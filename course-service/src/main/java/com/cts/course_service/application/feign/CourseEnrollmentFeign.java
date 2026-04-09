package com.cts.course_service.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "enrollment-service")
public interface CourseEnrollmentFeign {

    @PostMapping("/student-course-assignment/assign/{studentId}/{courseId}")
    void assignCourseToStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId);

    @GetMapping("/student-course-assignment/findCourseListBystudentId/{studentId}")
    List<Long> getCoursesListByStudentId(@PathVariable Long studentId);

}