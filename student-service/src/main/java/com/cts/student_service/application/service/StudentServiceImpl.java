package com.cts.student_service.application.service;

import com.cts.dto.request.StudentRegistrationDto;
import com.cts.student_service.application.entity.Student;
import com.cts.classexception.StudentException;
import com.cts.student_service.application.repository.StudentRepository;
import com.cts.student_service.application.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService{

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public String registerStudent(StudentRegistrationDto studentRegistrationDto) throws StudentException {
        log.info("Initiating student registration for user: {}", studentRegistrationDto.getUserEmail());
        log.debug("Extracting student and user entities from DTO");
        Student student = DtoMapper.studentDtoSeparator(studentRegistrationDto);

        // go for appUserRegistration
//        AppUser appUser = DtoMapper.appUserDtoSeparator(studentRegistrationDto,passwordEncoder);
        log.info("Extraction completed for student and user entities from DTO");
//        Optional<Role> role = roleRepository.findRoleByName("STUDENT");
//        appUser.setRole(role.get());
//        student.setAppUser(appUser);
        log.error("Attempting to register AppUser and save Student entity");
//        iAppUserService.registerAppUser(appUser);
        studentRepository.save(student);
        log.info("Successfully registered student. Assigned Student ID: {}", student.getStudentId());
//        return "Thanks for Registration, Your User id "; // return for testing
        return "Thanks for Registration, Your User Id is: "+student.getStudentId(); // return for development
    }
}
