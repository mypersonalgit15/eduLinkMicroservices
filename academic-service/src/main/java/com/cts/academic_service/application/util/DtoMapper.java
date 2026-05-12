package com.cts.academic_service.application.util;

import com.cts.academic_service.application.entity.Grade;
import com.cts.dto.request.ExamCreationRequestDto;
import com.cts.dto.request.GradeRegistration;
import com.cts.util.UIDGeneratorUtils;

import java.time.LocalDateTime;

public class DtoMapper {

    public static Grade gradeDto(GradeRegistration gradeRegistration,double totalScore) {
        Grade grade = new Grade();
        grade.setGradeId(UIDGeneratorUtils.uidGenerator());
        grade.setGrade(com.cts.util.GradeGenerator.generateGradeLetter(totalScore));
        grade.setScore(totalScore);
        grade.setStatus("COMPLETED");
        grade.setStudentId(gradeRegistration.getStudentId());
        grade.setCourseId(gradeRegistration.getCourseId());
        return grade;
    }
}