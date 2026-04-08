package com.cts.course_service.application.controller;

import com.cts.course_service.application.projection.CourseProjection;
import com.cts.course_service.application.service.ICourseService;
//import com.cts.dto.request.CourseEnrollmentDto;
import com.cts.dto.request.CourseRegistrationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
@Slf4j

public class CourseController {

    private final ICourseService iCourseService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCourse(@Valid @RequestBody CourseRegistrationDto courseRegistrationDto){
        log.info("{} request for a new course registration",courseRegistrationDto.getFacultyId());
        return  ResponseEntity.status(200).body(iCourseService.registerCourse(courseRegistrationDto));
    }

    @GetMapping("/getCoursesByFacultyId/{facultyId}")
    public ResponseEntity<List<CourseProjection>> getCoursesByFaculty(@Valid @PathVariable Long facultyId) {
        return ResponseEntity.status(200).body(iCourseService.getCoursesByFaculty(facultyId));
    }

    @GetMapping("/courseCount/{facultyId}")
    public Map<String, Integer> getFacultyCourseCount(@Valid @PathVariable Long facultyId) {
        int count = iCourseService.getFacultyCourseCount(facultyId);
        return Map.of("My Courses", count);
    }
}