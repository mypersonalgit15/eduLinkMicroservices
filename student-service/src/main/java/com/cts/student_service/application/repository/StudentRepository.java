
package com.cts.student_service.application.repository;

import com.cts.dto.response.StudentDetailByIdProjection;
import com.cts.student_service.application.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select count(s) > 0 from Student s where s.studentId = :studentId")
    boolean existsByStudentId(Long studentId);

    @Query("select s.appUserId from Student s where s.studentId = :studentId")
    Long findAppUserIdByStudentId(@Param("studentId") Long studentId);

    @Query("select s.studentId from Student s where s.appUserId = :appUserId")
    Long findStudentIdByAppUserId(@Param("appUserId") Long appUserId);

    @Query("select new com.cts.dto.response.StudentDetailByIdProjection" +
            "(s.studentId,s.studentDOB,s.studentGender,s.studentAddress,s.studentEnrollmentDateTime) " +
            "from Student s where s.studentId = :studentId")
    StudentDetailByIdProjection findStudentDetailByStudentId(@Param("studentId") Long studentId);
}
