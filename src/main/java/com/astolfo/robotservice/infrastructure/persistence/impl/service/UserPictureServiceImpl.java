package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.UserPictureMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserPictureEntity;
import com.astolfo.robotservice.domain.service.UserPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserPictureServiceImpl extends ServiceImpl<UserPictureMapper, UserPictureEntity> implements UserPictureService {
}
