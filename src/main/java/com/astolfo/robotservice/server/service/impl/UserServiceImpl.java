package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.UserMapper;
import com.astolfo.robotservice.server.model.dto.LoginRequest;
import com.astolfo.robotservice.server.model.entity.UserEntity;
import com.astolfo.robotservice.server.model.vo.LoginToken;
import com.astolfo.robotservice.server.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {




}
