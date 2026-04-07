package com.cts.iam_service.application.service;

import com.cts.dto.request.AppUserRegistrationDto;

public interface IAppUserService {
    Long appUserRegistration(AppUserRegistrationDto appUserRegistrationDto);
}
