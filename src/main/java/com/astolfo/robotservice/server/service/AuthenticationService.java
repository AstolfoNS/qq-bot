package com.astolfo.robotservice.server.service;

import com.astolfo.robotservice.server.common.userdetails.LoginUser;
import com.astolfo.robotservice.server.model.dto.LoginRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    void setAuthentication(LoginUser loginUser);

    Authentication getLoginRequestAuthenticationToken(LoginRequest loginRequest);

    Authentication getAuthenticationByLoginRequest(LoginRequest loginRequest);

    LoginUser getLoginUserByLoginRequest(LoginRequest loginRequest);

}
