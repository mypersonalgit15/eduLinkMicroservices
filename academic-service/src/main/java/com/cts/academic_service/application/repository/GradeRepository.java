package com.cts.academic_service.application.repository;


import com.cts.academic_service.application.entity.Grade;
import com.cts.dto.response.GradeDetailsByStudentIdDto;
import com.cts.dto.response.StudentGradeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    @Query("select g from Grade g where g.gradeId = :gradeId")
    Optional<Grade> findGradeById(@Param("gradeId") Long gradeId);

    @Query("select g from Grade g where g.studentId  =:studentId")
    Optional<Grade> checkStudentAvailableInGrade(@Param("studentId") Long studentId);

    @Query("select g.status from Grade g where g.gradeId = :gradeId")
    String findGradeStatus(@Param("gradeId") Long gradeId);

    @Query("select new com.cts.dto.response.StudentGradeProjection(g.score,g.grade) from Grade g where g.studentId = :studentId and g.courseId = :courseId")
    StudentGradeProjection findGradeByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("select g.status from Grade g where g.studentId = :studentId and g.courseId = :courseId")
    String findGradeStatusByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    @Query("select new com.cts.dto.response.GradeDetailsByStudentIdDto(g.courseId, g.score, g.grade) from Grade g where g.studentId = :studentId")
    List<GradeDetailsByStudentIdDto> findAllGradeByStudentId(@Param("studentId") Long studentId);

    @Query("select count(g) from Grade g where g.studentId = :studentId")
    int findAllGradeCountByStudentId(@Param("studentId") Long studentId);

}

