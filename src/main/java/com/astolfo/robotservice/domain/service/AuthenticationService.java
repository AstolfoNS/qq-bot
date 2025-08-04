package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequestDTO;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    void setAuthentication(LoginUser loginUser);

    Authentication getLoginRequestAuthenticationToken(LoginRequestDTO loginRequestDTO);

    Authentication getAuthenticationByLoginRequest(LoginRequestDTO loginRequestDTO);

    LoginUser getLoginUserByLoginRequest(LoginRequestDTO loginRequestDTO);

}
