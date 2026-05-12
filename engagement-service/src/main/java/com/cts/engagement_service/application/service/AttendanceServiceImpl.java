package com.cts.engagement_service.application.service;

import com.cts.dto.request.AttendanceRegistrationDto;
import com.cts.dto.response.CourseAttendanceProjection;
import com.cts.engagement_service.application.classexception.AttendanceException;
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
import org.springframework.http.HttpStatus;
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
            Long totalAttendedDays = attendanceRepository.countAttendanceByIdAndStudentId(courseId, studentId);
            Optional<Attendance> lastRecord = attendanceRepository
                    .findTopByCourseIdAndStudentIdOrderByLocalDateTimeDesc(courseId, studentId);

            if (totalAttendedDays > 0L && lastRecord.isPresent()) {
                LocalDateTime firstAttendanceDate = attendanceRepository.findFirstEnrollmentDate(courseId, studentId);
                        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                        firstAttendanceDate.toLocalDate(),
                        java.time.LocalDate.now()
                );
                long window = daysBetween + 1;
                double attendancePercentage = AttendanceCalculator.calculateAttendance(totalAttendedDays, window);
                course.setAttendancePercentage(attendancePercentage);
                course.setLastAttendanceDate(lastRecord.get().getLocalDateTime());
                log.info("Student {}: Course {} | Attended: {} | Window: {} | Rate: {}%",
                        studentId, courseId, totalAttendedDays, window, attendancePercentage);

            } else {
                course.setAttendancePercentage(0.0);
                course.setLastAttendanceDate(null);
            }
            courseAttendanceProjections.add(course);
        }
        return courseAttendanceProjections;
    }
    @Override
    @Transactional
    public String registerAttendanceByStudentId(AttendanceRegistrationDto attendanceRegistrationDto) throws AttendanceException {
        Long studentId = attendanceRegistrationDto.getStudentId();
        Long courseId = attendanceRegistrationDto.getCourseId();

        log.info("Initiating attendance registration - Student: {}, Course: {}", studentId, courseId);
        studentFeign.checkStudentExistByStudentId(studentId);
        courseFeign.checkCourseExistByCourseId(courseId);
        studentCourseEnrollmentFeign.checkStudentExistInCourse(studentId, courseId);

        Optional<Attendance> lastAttendance = attendanceRepository
                .findTopByCourseIdAndStudentIdOrderByLocalDateTimeDesc(courseId, studentId);

        if (lastAttendance.isPresent()) {
            LocalDateTime lastMarkedTime = lastAttendance.get().getLocalDateTime();
            LocalDateTime unlockTime = lastMarkedTime.plusHours(24);

            if (LocalDateTime.now().isBefore(unlockTime)) {
                log.warn("Lockout Active: Student {} tried to mark attendance too early. Next available: {}",
                        studentId, unlockTime);
                throw new AttendanceException("Attendance locked! You can mark it again after " + unlockTime.toString(), HttpStatus.BAD_REQUEST);
            }
        }

        Attendance attendance = DtoMapper.attendanceDtoSeparator(attendanceRegistrationDto);
        attendanceRepository.save(attendance);
        log.info("Attendance successfully recorded for Student ID: {}", studentId);
        return "Attendance recorded successfully!";
    }

    @Override
    public double findAttendancePercentageByCourseIdAndStudentId(Long courseId, Long studentId) {
        studentFeign.checkStudentExistByStudentId(studentId);
        courseFeign.checkCourseExistByCourseId(courseId);
        studentCourseEnrollmentFeign.checkStudentExistInCourse(studentId, courseId);

        Long totalAttendedDays = attendanceRepository.countAttendanceByIdAndStudentId(courseId, studentId);
        if (totalAttendedDays == 0L) {
            log.info("No attendance records found for Student {} in Course {}. Returning 0%.", studentId, courseId);
            return 0.0;
        }

        LocalDateTime firstAttendanceDate = attendanceRepository.findFirstEnrollmentDate(courseId, studentId);
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                firstAttendanceDate.toLocalDate(),
                java.time.LocalDate.now()
        );
        long window = daysBetween + 1;
        double attendancePercentage = AttendanceCalculator.calculateAttendance(totalAttendedDays, window);
        log.info("Calculated attendance for Student {} in Course {}: Attended: {}, Window: {}, Percentage: {}%",
                studentId, courseId, totalAttendedDays, window, attendancePercentage);
        return attendancePercentage;
    }
}
