package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.RoleMapper;
import com.astolfo.robotservice.server.model.entity.RoleEntity;
import com.astolfo.robotservice.server.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}
