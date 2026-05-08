package com.cts.auth_service.application.service;

import com.cts.auth_service.application.feign.FacultyServiceFeign;
import com.cts.auth_service.application.feign.IamServiceFeignClient;
import com.cts.auth_service.application.feign.StudentServiceFeign;
import com.cts.auth_service.security.util.JwtUtil;
import com.cts.dto.request.LoginDto;
import com.cts.dto.response.LoginResponseDto;
import com.cts.dto.response.UserAuthDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final IamServiceFeignClient iamServiceFeignClient;
    private final StudentServiceFeign studentServiceFeign;
    private final FacultyServiceFeign facultyServiceFeign;

    public LoginResponseDto login(LoginDto loginDto) {
        log.info("Login attempt initiated for email: {}", loginDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority().replace("ROLE_", "");
        log.debug("Fetching user details from IAM Service for email: {}", loginDto.getEmail());
        UserAuthDto userAuthDto = iamServiceFeignClient.findAppUserByEmail(loginDto.getEmail());
        Long userId = userAuthDto.getId();
        String userName = userAuthDto.getUserName();
        Long appUserId=null;
        if(role.equals("STUDENT")){
            appUserId = studentServiceFeign.findStudentIdByAppUserId(userId);
            if(appUserId == null) {
                log.error("Student record not found for AppUser ID: {}", userId);
                throw new RuntimeException("Student record not found. Please complete student registration.");
            }
        }else if(role.equals("FACULTY")){
            appUserId = facultyServiceFeign.findFacultyIdByAppUserId(userId);
            if(appUserId == null) {
                log.error("Faculty record not found for AppUser ID: {}", userId);
                throw new RuntimeException("Faculty record not found. Please complete faculty registration.");
            }
        }
        log.info("Generating JWT token for user: {}", userName);
        String token = jwtUtil.generateToken(userDetails, role, appUserId);

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        log.info("Login successful for user: {}", loginDto.getEmail());
        return response;
    }
}
