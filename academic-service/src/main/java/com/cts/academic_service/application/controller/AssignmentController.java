package com.cts.academic_service.application.controller;


import com.cts.academic_service.application.entity.AssignmentStatus;
import com.cts.academic_service.application.service.IAssignmentService;
import com.cts.dto.request.LearningMaterialRegistrationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
public class AssignmentController {

    private final IAssignmentService assignmentService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentStatus>> getAssignments(@Valid @PathVariable Long courseId, @RequestParam Long studentId) {
        List<AssignmentStatus> assignments = assignmentService.getAssignmentsForStudent(courseId, studentId);
        return ResponseEntity.ok(assignments);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
    @PostMapping("/{assignmentId}/complete")
    public ResponseEntity<String> completeAssignment(@Valid @PathVariable Long assignmentId, @RequestParam Long studentId) {
        assignmentService.completeAssignment(assignmentId, studentId);
        return ResponseEntity.ok("Assignment Completed");
    }


    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/course/{courseId}/exam-access")
    public ResponseEntity<Boolean> canTakeExam(@Valid @PathVariable Long courseId, @RequestParam Long studentId) {
        boolean canTake = assignmentService.canStudentTakeExam(courseId, studentId);
        return ResponseEntity.ok(canTake);
    }
}