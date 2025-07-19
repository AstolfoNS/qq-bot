package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.UserPhotoMapper;
import com.astolfo.robotservice.server.model.entity.UserPhotoEntity;
import com.astolfo.robotservice.server.service.UserPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserPhotoServiceImpl extends ServiceImpl<UserPhotoMapper, UserPhotoEntity> implements UserPhotoService {
}
