package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamProjection {
    private String examName;
    private LocalDateTime examLocalDateTime;
    private String examStatus;
    private int candidates;
}