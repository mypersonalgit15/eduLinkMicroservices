package com.cts.faculty_service.application.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "iam-service")
public interface AppUserFeign {

    @GetMapping("/appUser/findAppUserNameByAppUserId/{appUserId}")
    String findAppUserNameByAppUserId(@PathVariable Long appUserId);
}