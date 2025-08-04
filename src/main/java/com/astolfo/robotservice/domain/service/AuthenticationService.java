package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    void setAuthentication(LoginUser loginUser);

    Authentication getLoginRequestAuthenticationToken(LoginRequest loginRequest);

    Authentication getAuthenticationByLoginRequest(LoginRequest loginRequest);

    LoginUser getLoginUserByLoginRequest(LoginRequest loginRequest);

}
