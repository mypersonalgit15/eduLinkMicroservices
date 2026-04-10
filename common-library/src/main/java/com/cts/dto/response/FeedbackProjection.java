package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedbackProjection {
    private String appUserName;
    private String message;
    private Double rating;
}