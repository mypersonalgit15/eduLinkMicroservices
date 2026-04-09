package com.cts.course_service.application.controller;

import com.cts.course_service.application.projection.CourseDetailProjection;
import com.cts.course_service.application.service.ICourseService;
import com.cts.dto.request.CourseEnrollmentDto;
import com.cts.dto.request.CourseRegistrationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cts.dto.response.CourseDetailByIdProjection;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
@Slf4j

public class CourseController {

    private final ICourseService iCourseService;

    @GetMapping("/findCourseDetailsById/{courseId}")
    public ResponseEntity<CourseDetailByIdProjection> findCourseById(@Valid @PathVariable Long courseId) {
        log.info("User requested for details of courseId: {} ", courseId);
        return ResponseEntity.status(200).body(iCourseService.findCourseDetailsById(courseId));
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<String> updateCourse(@Valid @PathVariable Long courseId, @RequestBody CourseRegistrationDto courseRegistrationDto) {
        log.info("Received request to update course with ID: {}", courseId);
        String response = iCourseService.updateCourse(courseId, courseRegistrationDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/patch/{courseId}")
    public ResponseEntity<String> patchCourse(@Valid @PathVariable Long courseId, @RequestBody Map<String, Object> updates) {
        log.info("Received patch request for courseId: {}", courseId);
        String response = iCourseService.patchCourse(courseId, updates);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@Valid @PathVariable Long courseId) {
        log.info("Received request to delete course with ID: {}", courseId);
        String response = iCourseService.deleteCourse(courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allCourseListByStudentId/{studentId}")
    public ResponseEntity<List<CourseDetailProjection>> findCourseListByStudentId(@Valid @PathVariable Long studentId){
        log.info("Received GET request: Fetching courses for studentId: {}", studentId);
        return ResponseEntity.status(200).body(iCourseService.findCourseListByStudentId(studentId));
    }

    @PostMapping("/enrollmentRequest")
    public ResponseEntity<String> courseEnrollmentRequest(@Valid @RequestBody CourseEnrollmentDto courseEnrollmentDto){
        log.info("Received PATCH request: Enrollment attempt for Student: {} on Course: {}",courseEnrollmentDto.getStudentId(), courseEnrollmentDto.getCourseId());
        return ResponseEntity.status(200).body(iCourseService.courseEnrollmentRequest(courseEnrollmentDto));
    }
}