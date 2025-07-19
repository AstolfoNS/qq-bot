package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.PermissionMapper;
import com.astolfo.robotservice.server.model.entity.PermissionEntity;
import com.astolfo.robotservice.server.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
}
