package com.cts.faculty_service.application.service;

import com.cts.dto.request.FacultyRegistrationDto;
import com.cts.dto.response.CourseProjection;
import com.cts.dto.response.FacultyDetailProjection;

import java.util.List;

public interface IFacultyService {
    String registerFaculty(FacultyRegistrationDto facultyRegistrationDto);
    void checkFacultyExistByFacultyId(Long facultyId);
    FacultyDetailProjection getFacultyDetailsByFacultyId(Long facultyId);
    List<CourseProjection> getFacultyCourses(Long facultyId);
    String updateFacultyRating(Long facultyId, double newFacultyRating);
    String deleteFaculty(Long facultyId);
    String getFacultyNameByFacultyId(Long facultyId);
    Long findFacultyIdByAppUserId(Long appUserId);
}