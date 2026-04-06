package com.cts.iam_service.application.repository;

import com.cts.iam_service.application.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
}
