package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.infrastructure.utils.JwtUtil;
import com.astolfo.robotservice.infrastructure.utils.RedisCacheUtil;
import com.astolfo.robotservice.server.common.constants.RedisCacheConstant;
import com.astolfo.robotservice.server.common.userdetails.LoginUser;
import com.astolfo.robotservice.server.model.dto.LoginRequest;
import com.astolfo.robotservice.server.model.entity.UserEntity;
import com.astolfo.robotservice.server.model.vo.LoginToken;
import com.astolfo.robotservice.server.service.AuthenticationService;
import com.astolfo.robotservice.server.service.LoginService;
import com.astolfo.robotservice.server.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;


    @Override
    public LoginToken login(LoginRequest loginRequest) {
        LoginUser loginUser = authenticationService.getLoginUserByLoginRequest(loginRequest);

        try {
            redisCacheUtil.set(RedisCacheConstant.Login_USER_PERFIX.concat(loginUser.getIdToString()), loginUser);

            UserEntity userEntity = userService.getById(loginUser.getId());

            userEntity.setLastLoginTime(LocalDateTime.now());

            userService.updateById(userEntity);

            return new LoginToken(userEntity, jwtUtil.generateToken(loginUser));
        } catch (Exception exception) {
            log.error("登录失败，错误信息: {}", exception.getMessage());

            return null;
        }
    }
}

