package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.RolePermissionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.RolePermissionEntity;
import com.astolfo.robotservice.domain.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {
}
