package com.cts.student_service.application.service;

import com.cts.dto.StudentRegistrationDto;

public interface IStudentService {
    String registerStudent(StudentRegistrationDto studentRegistrationDto);
}
