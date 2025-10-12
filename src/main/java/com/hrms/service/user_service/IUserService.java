package com.hrms.service.user_service;

import com.hrms.dto.auth_dto.UserDetails;
import com.hrms.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);
    Optional<UserDetails> findUserDetailsByEmail(String email);
    boolean validateUserCredentials(String email, String password);
    void updateUserLoginInfo(User user);
}