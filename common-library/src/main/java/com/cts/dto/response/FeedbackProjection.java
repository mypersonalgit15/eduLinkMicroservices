package com.cts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FeedbackProjection {
    private Long appUserRoleId;
    private String appUserName;
    private String message;
    private Double rating;
}