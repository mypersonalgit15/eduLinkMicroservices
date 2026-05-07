package com.cts.faculty_service.application.util;

import com.cts.dto.request.FacultyRegistrationDto;
import com.cts.dto.response.AppUserDetailByIdDto;
import com.cts.dto.response.FacultyDetailByIdDto;
import com.cts.faculty_service.application.entity.Faculty;
import com.cts.util.UIDGeneratorUtils;

public class DtoMapper {
    public static Faculty facultyDtoSeparator(FacultyRegistrationDto facultyRegistrationDto){
        Faculty faculty = new Faculty();
        faculty.setFacultyGender(facultyRegistrationDto.getFacultyGender());
        faculty.setFacultyYearOfExperience(facultyRegistrationDto.getFacultyYearOfExperience());
        faculty.setFacultyAddress(facultyRegistrationDto.getFacultyAddress());
        faculty.setFacultyRating(0.0);
        faculty.setTotalFacultyRatingCount(0L);
        Long facultyId = UIDGeneratorUtils.uidGenerator();
        faculty.setFacultyId(facultyId);
        return faculty;
    }

    public static FacultyDetailByIdDto appUserFacultyDtoMerger(Faculty faculty, AppUserDetailByIdDto appUserDetailByIdDto){
        FacultyDetailByIdDto facultyDetailByIdDto = new FacultyDetailByIdDto();
        facultyDetailByIdDto.setFacultyId(faculty.getFacultyId());
        facultyDetailByIdDto.setFacultyName(appUserDetailByIdDto.getUserName());
        facultyDetailByIdDto.setFacultyEmail(appUserDetailByIdDto.getUserEmail());
        facultyDetailByIdDto.setFacultyPhoneNumber(appUserDetailByIdDto.getPhoneNumber());
        facultyDetailByIdDto.setFacultyGender(faculty.getFacultyGender());
        facultyDetailByIdDto.setFacultyYearOfExperience(faculty.getFacultyYearOfExperience());
        facultyDetailByIdDto.setFacultyAddress(faculty.getFacultyAddress());
        facultyDetailByIdDto.setFacultyRating(faculty.getFacultyRating());
        return facultyDetailByIdDto;
    }
}