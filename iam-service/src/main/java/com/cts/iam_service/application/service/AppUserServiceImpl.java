package com.cts.iam_service.application.service;

import com.cts.dto.request.StudentRegistrationDto;
import com.cts.iam_service.application.entity.AppUser;
import com.cts.iam_service.application.repository.AppUserRepository;
import com.cts.iam_service.application.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class AppUserServiceImpl implements IAppUserService{
    private final AppUserRepository appUserRepository;
    @Override
    public Long appUserRegistration(StudentRegistrationDto studentRegistrationDto) {
        AppUser appUser = DtoMapper.appUserDtoSeparator(studentRegistrationDto);
        appUserRepository.save(appUser);
        return appUser.getId();
    }
}
