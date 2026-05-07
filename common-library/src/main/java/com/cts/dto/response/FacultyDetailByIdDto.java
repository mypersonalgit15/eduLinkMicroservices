package com.cts.dto.response;
import lombok.Data;

@Data
public class FacultyDetailByIdDto {
    private Long facultyId;
    private  String facultyName;
    private  String facultyEmail;
    private  Long facultyPhoneNumber;
    private  String facultyGender;
    private  int facultyYearOfExperience;
    private  String facultyAddress;
    private  double facultyRating;

}