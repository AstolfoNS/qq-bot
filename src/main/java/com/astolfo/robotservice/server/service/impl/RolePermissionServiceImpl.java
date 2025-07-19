package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.RolePermissionMapper;
import com.astolfo.robotservice.server.model.entity.RolePermissionEntity;
import com.astolfo.robotservice.server.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {
}
