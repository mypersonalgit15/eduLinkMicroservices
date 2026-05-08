package com.cts.auth_service.application.feign;

import com.cts.dto.response.AppUserDetailByIdDto;
import com.cts.dto.response.UserAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "iam-service")
public interface IamServiceFeignClient {

    @GetMapping("/appUser/findAppUserByEmail/{email}")
    UserAuthDto findAppUserByEmail(@PathVariable String email);
}
