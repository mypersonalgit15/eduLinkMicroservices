package com.cts.auth_service.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "faculty-service")
public interface FacultyServiceFeign {

    @GetMapping("/faculty/findFacultyIdByAppUserId/{appUserId}")
    Long findFacultyIdByAppUserId(@PathVariable Long appUserId);
}
