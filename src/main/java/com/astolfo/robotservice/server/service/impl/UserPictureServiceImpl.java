package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.UserPictureMapper;
import com.astolfo.robotservice.server.model.entity.UserPictureEntity;
import com.astolfo.robotservice.server.service.UserPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserPictureServiceImpl extends ServiceImpl<UserPictureMapper, UserPictureEntity> implements UserPictureService {
}
