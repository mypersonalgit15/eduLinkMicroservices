package com.cts.faculty_service.application.controller;

import com.cts.dto.request.FacultyRegistrationDto;
import com.cts.dto.response.CourseProjection;
import com.cts.dto.response.FacultyDetailByIdDto;
import com.cts.faculty_service.application.service.IFacultyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@AllArgsConstructor
@Slf4j
public class FacultyController {
    private final IFacultyService facultyService;

    @PostMapping("/register")
    public ResponseEntity<String> registerFaculty(@Valid @RequestBody FacultyRegistrationDto facultyRegistrationDto){
        log.info("{} has initiated the registration as a Faculty",facultyRegistrationDto.getUserEmail());
        return ResponseEntity.status(200).body(facultyService.registerFaculty(facultyRegistrationDto));
    }

    @GetMapping("/checkFacultyExistByFacultyId/{facultyId}")
    public void checkFacultyExistByFacultyId(@PathVariable Long facultyId){
        log.info("Request has been initiated to get Faculty details by facultyId {}",facultyId);
        facultyService.checkFacultyExistByFacultyId(facultyId);
    }

    @GetMapping("/getFacultyNameByFacultyId/{facultyId}")
    public String getFacultyNameByFacultyId(@PathVariable Long facultyId){
        log.info("Request has been initiated to get Faculty name by facultyId {}",facultyId);
        return facultyService.getFacultyNameByFacultyId(facultyId);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
    @GetMapping("/getFacultyDetailsByFacultyId/{facultyId}")
    public ResponseEntity<FacultyDetailByIdDto> getFacultyDetailsByFacultyId(@PathVariable Long facultyId) {
        log.info("Request has been initiated to get Faculty details by facultyId {}", facultyId);
        return ResponseEntity.status(200).body(facultyService.getFacultyDetailsByFacultyId(facultyId));
    }

    @GetMapping("/getFacultyCourses/{facultyId}")
    public ResponseEntity<List<CourseProjection>> getFacultyCourses(@Valid @PathVariable Long facultyId) {
        log.info("Received request to get courses for faculty with ID: {}", facultyId);
        List<CourseProjection> courses = facultyService.getFacultyCourses(facultyId);
        return ResponseEntity.status(200).body(courses);
    }

    @GetMapping("/findFacultyIdByAppUserId/{appUserId}")
    public Long findFacultyIdByAppUserId(@PathVariable Long appUserId){
        return facultyService.findFacultyIdByAppUserId(appUserId);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/updateRating/{facultyId}/{newFacultyRating}")
    public ResponseEntity<String> updateFacultyRating(@Valid @PathVariable Long facultyId, @PathVariable double newFacultyRating){
        return ResponseEntity.status(200).body(facultyService.updateFacultyRating(facultyId,newFacultyRating));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{facultyId}")
    public ResponseEntity<String> deleteFaculty(@Valid @PathVariable Long facultyId) {
        log.info("Received request to delete faculty with ID: {}", facultyId);
        String response = facultyService.deleteFaculty(facultyId);
        return ResponseEntity.status(200).body(response);
    }
}