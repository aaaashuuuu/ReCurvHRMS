package com.hrms.service.auth_service;

import com.hrms.dto.auth_dto.LoginRequest;
import com.hrms.dto.auth_dto.LoginResponse;

public interface IAuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
    LoginResponse processOAuth2Login(String email);
    boolean isUserActive(String email);
}