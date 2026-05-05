package com.cts.auth_service.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentServiceFeign {

    @GetMapping("/student/findStudentIdByAppUserId/{appUserId}")
    Long findStudentIdByAppUserId(@PathVariable Long appUserId);
}
