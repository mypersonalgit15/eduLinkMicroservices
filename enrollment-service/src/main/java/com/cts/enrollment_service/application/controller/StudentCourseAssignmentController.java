package com.cts.enrollment_service.application.controller;

import com.cts.enrollment_service.application.service.IStudentCourseEnrollmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/student-course-assignment")
public class StudentCourseAssignmentController {
    private final IStudentCourseEnrollmentService studentCourseEnrollmentService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/assign/{studentId}/{courseId}")
    public void assignCourseToStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        log.info("Received request to assign course with ID {} to student with ID {}", courseId, studentId);
        studentCourseEnrollmentService.assignCourseToStudent(studentId, courseId);
        log.info("Successfully assigned course with ID {} to student with ID {}", courseId, studentId);
    }

    @GetMapping("/checkEnrollment/{studentId}/{courseId}")
    public boolean checkStudentExistInCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        log.info("Received request to check if student with ID {} is enrolled in course with ID {}", studentId, courseId);
        return studentCourseEnrollmentService.checkStudentExistInCourse(studentId, courseId);
    }

    @GetMapping("/findCourseListBystudentId/{studentId}")
    public List<Long> getCoursesListByStudentId(@PathVariable Long studentId) {
        log.info("Received request to get courses assigned to student with ID {}", studentId);
        List<Long> courses = studentCourseEnrollmentService.getEnrolledCourseIdsByStudentId(studentId);
        log.info("Successfully retrieved list of courses assigned to student with ID {}", studentId);
        return courses;
    }
}
