package com.cts.engagement_service.application.util;

import com.cts.dto.request.AttendanceRegistrationDto;
import com.cts.dto.request.FeedbackDto;
import com.cts.engagement_service.application.entity.Attendance;
import com.cts.engagement_service.application.entity.FeedBack;

import java.time.LocalDateTime;

public class DtoMapper {
    public static FeedBack feedBackDtoSeparator(FeedbackDto feedbackDto){
        FeedBack feedBack = new FeedBack();
        feedBack.setMessage(feedbackDto.getComment());
        feedBack.setRating(feedbackDto.getRating());
        return feedBack;
    }
    public static Attendance attendanceDtoSeparator(AttendanceRegistrationDto dto) {
        Attendance attendance = new Attendance();
        // CRITICAL: Transfer the IDs from the DTO to the Entity
        attendance.setStudentId(dto.getStudentId());
        attendance.setCourseId(dto.getCourseId());

        // Set the timestamp
        attendance.setLocalDateTime(LocalDateTime.now());
        return attendance;
    }
}