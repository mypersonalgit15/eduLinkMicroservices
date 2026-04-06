package com.cts.course_service.application.service;

import com.cts.course_service.application.entity.Course;
import com.cts.course_service.application.repository.CourseRepository;
import com.cts.dto.request.CourseRegistrationDto;
import com.cts.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class CourseServiceImpl implements ICourseService{

private final CourseRepository courseRepository;

@Override
@Transactional
public String registerCourse(CourseRegistrationDto courseRegistrationDto) throws FacultyException {
    log.info("Course registration has intercepted inside service");
    Optional<Long> facultyOption = facultyRepository.findFacultyById(courseRegistrationDto.getFacultyId());
    if (facultyOption.isEmpty()) {
        log.error("{} is not authorized to register course", courseRegistrationDto.getFacultyId());
        throw new FacultyException(courseRegistrationDto.getFacultyId() + " is not registered", HttpStatus.BAD_REQUEST);
    }
    Course course = DtoMapper.courseDtoSeparator(courseRegistrationDto);
    log.error("Unable to separate faculty from courseRegistrationDto");
    course.setCourseStatus("ACTIVE");
    course.getFacultySet().add(facultyOption.get());
    facultyOption.get().getCourseSet().add(course);
    courseRepository.save(course);
    log.info("Course with id {} saved successFully into database", course.getCourseId());
    return "Course has registered successFully with course Id: " + course.getCourseId();
}

}
