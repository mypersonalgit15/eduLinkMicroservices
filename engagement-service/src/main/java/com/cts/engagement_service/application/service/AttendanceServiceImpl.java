package com.cts.engagement_service.application.service;

import com.cts.dto.request.AttendanceRegistrationDto;
import com.cts.dto.response.CourseAttendanceProjection;
import com.cts.engagement_service.application.dtoMapper.DtoMapper;
import com.cts.engagement_service.application.entity.Attendance;
import com.cts.engagement_service.application.feign.CourseFeign;
import com.cts.engagement_service.application.feign.StudentCourseEnrollmentFeign;
import com.cts.engagement_service.application.feign.StudentFeign;
import com.cts.engagement_service.application.repository.AttendanceRepository;
import com.cts.util.DateUtils;
import com.cts.util.AttendanceCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements IAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentFeign studentFeign;
    private final CourseFeign courseFeign;
    private final StudentCourseEnrollmentFeign studentCourseEnrollmentFeign;


    @Override
    public List<CourseAttendanceProjection> findAttendanceByCourse(Long studentId) {
        studentFeign.checkStudentExistByStudentId(studentId);
        List<Long> studentCourseRegisteredList = studentCourseEnrollmentFeign.getCoursesListByStudentId(studentId);
        List<CourseAttendanceProjection> courseAttendanceProjections = new ArrayList<>();

        for (Long courseId : studentCourseRegisteredList) {
            CourseAttendanceProjection course = new CourseAttendanceProjection();
            course.setCourseId(courseId);
            course.setCourseTitle(courseFeign.findCourseTitleByCourseId(courseId));

            // 1. Get the total count of markings in the DB
            Long totalAttendedDays = attendanceRepository.countAttendanceByIdAndStudentId(courseId, studentId);

            // 2. Fetch the LATEST record for the 24-hour lockout check in Angular
            Optional<Attendance> lastRecord = attendanceRepository
                    .findTopByCourseIdAndStudentIdOrderByLocalDateTimeDesc(courseId, studentId);

            if (totalAttendedDays > 0L && lastRecord.isPresent()) {
                LocalDateTime firstAttendanceDate = attendanceRepository.findFirstEnrollmentDate(courseId, studentId);

                // 3. FIXED CALCULATION:
                // Use toLocalDate() to compare calendar dates, not timestamps.
                // If firstAttendance is May 7 and now is May 7, daysBetween is 0.
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                        firstAttendanceDate.toLocalDate(),
                        java.time.LocalDate.now()
                );

                // 4. The window must be at least 1 (the first day of attendance)
                long window = daysBetween + 1;

                // 5. Calculate the percentage based on Attended vs. Days Elapsed
                double attendancePercentage = AttendanceCalculator.calculateAttendance(totalAttendedDays, window);

                course.setAttendancePercentage(attendancePercentage);
                course.setLastAttendanceDate(lastRecord.get().getLocalDateTime());

                log.info("Student {}: Course {} | Attended: {} | Window: {} | Rate: {}%",
                        studentId, courseId, totalAttendedDays, window, attendancePercentage);

            } else {
                // No attendance marked yet
                course.setAttendancePercentage(0.0);
                course.setLastAttendanceDate(null);
            }

            courseAttendanceProjections.add(course);
        }
        return courseAttendanceProjections;
    }
    @Override
    @Transactional
    public String registerAttendanceByStudentId(AttendanceRegistrationDto attendanceRegistrationDto) {
        Long studentId = attendanceRegistrationDto.getStudentId();
        Long courseId = attendanceRegistrationDto.getCourseId();

        log.info("Initiating attendance registration - Student: {}, Course: {}", studentId, courseId);

        // 1. External Validations (Feign Calls)
        // Check if student exists, course exists, and student is actually enrolled
        studentFeign.checkStudentExistByStudentId(studentId);
        courseFeign.checkCourseExistByCourseId(courseId);
        studentCourseEnrollmentFeign.checkStudentExistInCourse(studentId, courseId);

        // 2. 24-Hour Lockout Logic
        // We fetch the most recent record based on the descending LocalDateTime
        Optional<Attendance> lastAttendance = attendanceRepository
                .findTopByCourseIdAndStudentIdOrderByLocalDateTimeDesc(courseId, studentId);

        if (lastAttendance.isPresent()) {
            LocalDateTime lastMarkedTime = lastAttendance.get().getLocalDateTime();
            LocalDateTime unlockTime = lastMarkedTime.plusHours(24);

            if (LocalDateTime.now().isBefore(unlockTime)) {
                log.warn("Lockout Active: Student {} tried to mark attendance too early. Next available: {}",
                        studentId, unlockTime);

                // Returning a clear string for the Angular frontend to display
                return "Attendance locked! You can mark it again after " + unlockTime.toString();
            }
        }

        // 3. Map and Save using DtoMapper
        // The DtoMapper now handles setting IDs and the current timestamp
        Attendance attendance = DtoMapper.attendanceDtoSeparator(attendanceRegistrationDto);

        attendanceRepository.save(attendance);

        log.info("Attendance successfully recorded for Student ID: {}", studentId);
        return "Attendance recorded successfully!";
    }
}
