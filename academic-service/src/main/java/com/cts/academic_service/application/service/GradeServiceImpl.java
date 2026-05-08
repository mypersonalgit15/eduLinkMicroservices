package com.cts.academic_service.application.service;


import com.cts.academic_service.application.entity.Grade;
import com.cts.academic_service.application.feign.AttendanceFeign;
import com.cts.academic_service.application.repository.GradeRepository;
import com.cts.academic_service.application.util.DtoMapper;
import com.cts.classexception.GradeException;
import com.cts.dto.request.GradeRegistration;
import com.cts.dto.response.StudentGradeProjection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GradeServiceImpl implements IGradeService{
    private final GradeRepository gradeRepository;
    private final AttendanceFeign attendanceFeign;

    @Override
    public String registerGrade(GradeRegistration gradeRegistration) {
        String gradeStatus = gradeRepository.findGradeStatusByCourseIdAndStudentId(gradeRegistration.getCourseId(), gradeRegistration.getStudentId());
        if(gradeStatus.equals("COMPLETED")){
            log.warn("Grade registration skipped: Student ID {} already has a grade for Course ID {}", gradeRegistration.getStudentId(), gradeRegistration.getCourseId());
            return "You have already submitted exam for this course.";
        }
        log.info("Starting grade registration for Student ID: {} in Course ID: {}",gradeRegistration.getStudentId(), gradeRegistration.getCourseId());
        double attendancePercentage  = attendanceFeign.findAttendancePercentageByCourseIdAndStudentId(gradeRegistration.getCourseId(), gradeRegistration.getStudentId());
        log.debug("Fetched attendance percentage from Feign: {}%", attendancePercentage);
        double attendanceScoreWeighted = attendancePercentage * 0.7;
        Grade grade = DtoMapper.gradeDto(gradeRegistration, attendanceScoreWeighted+30);
        gradeRepository.save(grade);
        log.info("Grade successfully saved to database for Student ID: {}", gradeRegistration.getStudentId());
        return "Thanks for examining! Your grade is registered successfully.";
    }

    @Override
    public String findGradeStatus(Long gradeId) throws GradeException {
        log.info("Fetching grade status for ID: {}", gradeId);
        Optional<Grade> grade = gradeRepository.findGradeById(gradeId);
        if(grade.isEmpty()){
            log.error("Grade lookup failed: No assignment found with ID {}", gradeId);
            throw new GradeException("NO assignment available with id: "+gradeId, HttpStatus.NOT_FOUND);
        }
        String status = gradeRepository.findGradeStatus(gradeId);
        log.debug("Successfully retrieved status '{}' for grade ID: {}", status, gradeId);
        return status;
    }

    @Override
    public StudentGradeProjection findTotalGradeByStudentId(Long studentId, Long courseId) throws GradeException {
        log.info("Calculating total grade for student ID: {}", studentId);
        Optional<Grade> grade = gradeRepository.checkStudentAvailableInGrade(studentId);
        if(grade.isEmpty()){
            log.warn("Grade calculation aborted: Student ID {} has no recorded tests.", studentId);
            throw new GradeException(studentId+" is not given any test yet!",HttpStatus.NOT_FOUND);
        }
        StudentGradeProjection studentGradeProjection = gradeRepository.findGradeByStudentIdAndCourseId(studentId,courseId);
        log.info("Total grade for student ID {}: {}", studentId, studentGradeProjection.getGrade());
        return studentGradeProjection;
    }
}
