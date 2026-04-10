package com.cts.iam_service.application.service;

import com.cts.dto.request.AppUserRegistrationDto;
import com.cts.dto.response.AppUserDetailByIdDto;

public interface IAppUserService {
    String findAppUserNameByAppUserId(Long appUserId);
    AppUserDetailByIdDto findAppUserDetailsByAppUserId(Long appUserId);
    Long appUserRegistration(AppUserRegistrationDto appUserRegistrationDto);
}
