package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.common.userdetails.LoginUser;
import com.astolfo.robotservice.server.mapper.UserMapper;
import com.astolfo.robotservice.server.model.entity.PermissionEntity;
import com.astolfo.robotservice.server.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = Optional
                .ofNullable(userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, username)))
                .orElseThrow(() -> {
                    log.error("用户登录失败, 未找到该用户名: {}", username);

                    return new UsernameNotFoundException(username + " not found");
                });

        List<String>    loginUserAuthorityList  =   Optional
                .ofNullable(userMapper.getPermissionListByUsername(username))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PermissionEntity::getSymbol)
                .toList();
        Long            loginUserId             =   userEntity.getId();
        String          loginUserUsername       =   userEntity.getUsername();
        String          loginUserPassword       =   userEntity.getPassword();

        return new LoginUser(loginUserId, loginUserUsername, loginUserPassword, loginUserAuthorityList);
    }
}
