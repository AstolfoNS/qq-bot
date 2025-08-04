package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.UserMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserEntity;
import com.astolfo.robotservice.domain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {




}
