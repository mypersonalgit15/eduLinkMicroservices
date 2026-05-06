package com.cts.student_service.application.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    
                    HttpServletRequest request = attributes.getRequest();

                    String authHeader = request.getHeader("Authorization");
                    if (authHeader != null) {
                        template.header("Authorization", authHeader);
                    }

                    String email = request.getHeader("X-User-Email");
                    String role = request.getHeader("X-User-Role");
                    String appUserId = request.getHeader("X-App-User-Id");

                    if (email != null) template.header("X-User-Email", email);
                    if (role != null) template.header("X-User-Role", role);
                    if (appUserId != null) template.header("X-App-User-Id", appUserId);
                }
            }
        };
    }
}