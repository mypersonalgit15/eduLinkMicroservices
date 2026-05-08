package com.cts.academic_service.application.controller;


import com.cts.academic_service.application.service.IGradeService;
import com.cts.dto.request.GradeRegistration;
import com.cts.dto.response.StudentGradeProjection;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/grade")
public class GradeController {

    private final IGradeService gradeService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/register")
    public ResponseEntity<String> registerGrade(@RequestBody @Valid GradeRegistration gradeRegistration){
        log.info("Attempting to register grade for student ID: {}", gradeRegistration.getStudentId());
        return ResponseEntity.status(200).body(gradeService.registerGrade(gradeRegistration));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/status/{gradeId}")
    public ResponseEntity<String> getGradeStatusById(@Valid @PathVariable Long gradeId){
        log.info("API call: Fetching status for grade ID: {}", gradeId);
        return ResponseEntity.status(200).body(gradeService.findGradeStatus(gradeId));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/totalGrade/{studentId}/{courseId}")
    public ResponseEntity<StudentGradeProjection> findTotalGradeByStudentId(@Valid @PathVariable Long studentId, @PathVariable Long courseId){
        log.info("API call: Calculating total grade for student ID: {}", studentId);
        return ResponseEntity.status(200).body(gradeService.findTotalGradeByStudentId(studentId,courseId));
    }
}


