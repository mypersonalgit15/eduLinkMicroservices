package com.cts.student_service.application.util;

import com.cts.dto.request.StudentRegistrationDto;
import com.cts.dto.response.AppUserDetailByIdDto;
import com.cts.dto.response.StudentDetailByIdDto;
import com.cts.dto.response.StudentDetailByIdProjection;
import com.cts.student_service.application.entity.Student;
import com.cts.util.UIDGeneratorUtils;

import java.time.LocalDateTime;

public class DtoMapper {
    public static Student studentDtoSeparator(StudentRegistrationDto studentDto){
        Student student = new Student();
        student.setStudentAddress(studentDto.getStudentAddress());
        student.setStudentDOB(studentDto.getStudentDOB());
        student.setStudentGender(studentDto.getStudentGender());
        student.setStudentEnrollmentDateTime(LocalDateTime.now());
        Long studentId = UIDGeneratorUtils.uidGenerator();
        student.setStudentId(studentId);
        return student;
    }
    public static StudentDetailByIdDto appUserStudentDtoMerger(AppUserDetailByIdDto appUserDetailByIdDto, StudentDetailByIdProjection studentDetailByIdProjection){
        StudentDetailByIdDto studentDetailByIdDto = new StudentDetailByIdDto();
        studentDetailByIdDto.setStudentName(appUserDetailByIdDto.getUserName());
        studentDetailByIdDto.setStudentEmail(appUserDetailByIdDto.getUserEmail());
        studentDetailByIdDto.setStudentNumber(appUserDetailByIdDto.getPhoneNumber());
        studentDetailByIdDto.setStudentId(studentDetailByIdProjection.getStudentId());
        studentDetailByIdDto.setStudentDOB(studentDetailByIdProjection.getStudentDOB());
        studentDetailByIdDto.setStudentGender(studentDetailByIdProjection.getStudentGender());
        studentDetailByIdDto.setStudentAddress(studentDetailByIdProjection.getStudentAddress());
        studentDetailByIdDto.setStudentEnrollmentDateTime(studentDetailByIdProjection.getStudentEnrollmentDateTime());
        return studentDetailByIdDto;
    }
}
