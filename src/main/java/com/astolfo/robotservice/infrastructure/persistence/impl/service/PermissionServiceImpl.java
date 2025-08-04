package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.PermissionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.PermissionEntity;
import com.astolfo.robotservice.domain.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
}
