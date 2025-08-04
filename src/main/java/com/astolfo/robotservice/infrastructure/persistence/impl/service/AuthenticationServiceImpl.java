package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequest;
import com.astolfo.robotservice.domain.service.AuthenticationService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthenticationManager authenticationManager;


    @Override
    public void setAuthentication(LoginUser loginUser) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()));
    }

    @Override
    public Authentication getLoginRequestAuthenticationToken(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Override
    public Authentication getAuthenticationByLoginRequest(LoginRequest loginRequest) {
        return authenticationManager.authenticate(this.getLoginRequestAuthenticationToken(loginRequest));
    }

    @Override
    public LoginUser getLoginUserByLoginRequest(LoginRequest loginRequest) {
        return (LoginUser) this.getAuthenticationByLoginRequest(loginRequest).getPrincipal();
    }

}
