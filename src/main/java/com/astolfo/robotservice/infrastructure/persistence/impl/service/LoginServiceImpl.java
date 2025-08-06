package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.common.utils.JwtUtils;
import com.astolfo.robotservice.infrastructure.common.utils.RedisCacheUtils;
import com.astolfo.robotservice.infrastructure.common.constants.RedisCacheConstant;
import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequestDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserEntity;
import com.astolfo.robotservice.infrastructure.persistence.model.vo.LoginTokenVO;
import com.astolfo.robotservice.domain.service.AuthenticationService;
import com.astolfo.robotservice.domain.service.LoginService;
import com.astolfo.robotservice.domain.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;


    @Override
    public LoginTokenVO login(LoginRequestDTO loginRequestDTO) {
        LoginUser loginUser = authenticationService.getLoginUserByLoginRequest(loginRequestDTO);

        try {
            redisCacheUtils.set(RedisCacheConstant.Login_USER_PERFIX.concat(loginUser.getIdToString()), loginUser);

            UserEntity userEntity = userService.getById(loginUser.getId());

            userEntity.setLastLoginTime(LocalDateTime.now());

            userService.updateById(userEntity);

            return new LoginTokenVO(userEntity, jwtUtils.generateToken(loginUser));
        } catch (Exception exception) {
            log.error("登录失败，错误信息: {}", exception.getMessage());

            return null;
        }
    }
}

