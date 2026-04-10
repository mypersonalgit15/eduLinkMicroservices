package com.cts.faculty_service.application.service;

import com.cts.classexception.FacultyException;
import com.cts.dto.request.FacultyRegistrationDto;
import com.cts.dto.response.CourseProjection;
import com.cts.dto.response.FacultyDetailProjection;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface IFacultyService {
    String registerFaculty(FacultyRegistrationDto facultyRegistrationDto);
    void checkFacultyExistByFacultyId(Long facultyId);
    FacultyDetailProjection getFacultyDetailsByFacultyId(Long facultyId);
    List<CourseProjection> getFacultyCourses(Long facultyId);
    public String deleteFaculty(Long facultyId);




}