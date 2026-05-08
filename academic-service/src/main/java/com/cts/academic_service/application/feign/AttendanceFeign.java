package com.cts.academic_service.application.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "engagement-service")
public interface AttendanceFeign {
    @GetMapping("/attendance/attendancePercentage/{courseId}/{studentId}")
    double findAttendancePercentageByCourseIdAndStudentId(@Valid @PathVariable Long courseId, @Valid @PathVariable Long studentId);
}
