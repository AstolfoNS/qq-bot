package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequestDTO;
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
    public Authentication getLoginRequestAuthenticationToken(LoginRequestDTO loginRequestDTO) {
        return new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    }

    @Override
    public Authentication getAuthenticationByLoginRequest(LoginRequestDTO loginRequestDTO) {
        return authenticationManager.authenticate(this.getLoginRequestAuthenticationToken(loginRequestDTO));
    }

    @Override
    public LoginUser getLoginUserByLoginRequest(LoginRequestDTO loginRequestDTO) {
        return (LoginUser) this.getAuthenticationByLoginRequest(loginRequestDTO).getPrincipal();
    }

}
