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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, username));

        if (Objects.isNull(userEntity)) {
            log.error("{} not found", username);

            throw new UsernameNotFoundException(username + " not found");
        }

        LoginUser loginUser = new LoginUser();

        loginUser.setUsername(username);

        loginUser.setPassword(userEntity.getPassword());

        List<PermissionEntity> permissionEntityList = userMapper.getPermissionListByUsername(username);

        if (CollectionUtils.isEmpty(permissionEntityList)) {
            loginUser.setAuthorityList(new ArrayList<>());
        }

        List<String> permissionSymbolList = permissionEntityList
                .stream()
                .map(PermissionEntity::getSymbol)
                .toList();

        loginUser.setAuthorityList(permissionSymbolList);

        return loginUser;
    }
}
