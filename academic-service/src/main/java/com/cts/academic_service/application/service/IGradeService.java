package com.cts.academic_service.application.service;

import com.cts.dto.request.GradeRegistration;
import com.cts.dto.response.StudentGradeProjection;

public interface IGradeService {
    String registerGrade(GradeRegistration gradeRegistration);
    String findGradeStatus(Long gradeId);
    StudentGradeProjection findTotalGradeByStudentId(Long studentId, Long courseId);
}
