package com.cts.course_service.application.feign;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FacultyFeign {
    @PostMapping("/register")
    public ResponseEntity<String> registerFaculty(@Valid @RequestBody FacultyRegistrationDto facultyRegistrationDto);
}
