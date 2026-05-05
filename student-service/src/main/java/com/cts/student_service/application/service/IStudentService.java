package com.cts.student_service.application.service;

import com.cts.dto.request.StudentRegistrationDto;
import com.cts.dto.response.StudentDetailByIdDto;
import org.springframework.data.repository.query.Param;

public interface IStudentService {
    String registerStudent(StudentRegistrationDto studentRegistrationDto);
    String checkStudentExistByStudentId(Long studentId);
    String getStudentNameByStudentId(Long studentId);
    Long findStudentIdByAppUserId(Long appUserId);
    StudentDetailByIdDto findStudentDetailByStudentId(Long studentId);
}