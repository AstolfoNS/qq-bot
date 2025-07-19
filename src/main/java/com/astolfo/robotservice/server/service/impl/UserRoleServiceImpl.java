package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.UserRoleMapper;
import com.astolfo.robotservice.server.model.entity.UserRoleEntity;
import com.astolfo.robotservice.server.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
}
