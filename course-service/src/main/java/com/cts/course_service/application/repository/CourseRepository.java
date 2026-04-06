package com.cts.course_service.application.repository;

import com.cts.course_service.application.entity.Course;
import com.cts.projection.CourseDetailByIdProjection;
import com.cts.projection.CourseDetailProjection;
import com.cts.projection.CourseProjection;
import com.cts.projection.CourseSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query(" select new com.cts.eduLink.application.projection.CourseProjection(c.courseTitle," +
            "c.courseSubject,c.courseGradeLevel,c.courseCredit,c.courseStatus,c.courseRating) from Course c where c.courseStatus='ACTIVE'")
    List<CourseProjection> findAllAvailableCourse();

    Optional<Course> findByCourseId(Long courseId);

    @Query("SELECT c FROM Course c JOIN c.facultySet f WHERE f.facultyId = :facultyId")
    List<CourseProjection> findCoursesByFacultyId(@Param("facultyId") Long facultyId);

    @Query("SELECT COUNT(c) FROM Course c JOIN c.facultySet f WHERE f.facultyId = :facultyId")
    int getFacultyCourseCount(@Param("facultyId") Long facultyId);

    @Query(" select new com.cts.eduLink.application.projection.CourseDetailByIdProjection(c.courseId,c.courseTitle," +
            "c.courseSubject,c.courseGradeLevel,c.courseCredit,c.courseStatus,c.courseRating,a.userName,f.facultyRating)"+
            " from Course c inner join c.facultySet f inner join f.appUser a where c.courseId = :courseId")
    Optional<CourseDetailByIdProjection> findCourseDetailsById(@Param("courseId") Long courseId);

    @Query("select new com.cts.eduLink.application.projection.CourseSummaryProjection(c.id, c.courseId, c.courseTitle)"+" from Course c"+
            " inner join c.studentSet s where s.studentId = :studentId")
    List<CourseSummaryProjection> findCourseSummaryListByStudentId(@Param("studentId") Long studentId);

    @Query("select c from Course c where c.courseId = :courseId")
    Optional<Course> findCourseById(@Param("courseId") Long courseId);

    @Query(" select new com.cts.eduLink.application.projection.CourseDetailProjection(c.courseTitle," +
            "c.courseGradeLevel,c.courseRating) from Course c inner join c.studentSet s where s.studentId = :studentId")
    List<CourseDetailProjection> findCourseListByStudentId(@Param("studentId") Long studentId);

}
