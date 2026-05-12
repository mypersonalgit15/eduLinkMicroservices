package com.cts.academic_service.application.service;

import com.cts.dto.request.GradeRegistration;
import com.cts.dto.response.GradeDetailsByStudentIdDto;
import com.cts.dto.response.StudentGradeProjection;

import java.util.List;

public interface IGradeService {
    String registerGrade(GradeRegistration gradeRegistration);
    String findGradeStatus(Long gradeId);
    StudentGradeProjection findTotalGradeByStudentId(Long studentId, Long courseId);
    List<GradeDetailsByStudentIdDto> findAllGradesByStudentId(Long studentId);
    int findAllGradesCountByStudentId(Long studentId);
}
