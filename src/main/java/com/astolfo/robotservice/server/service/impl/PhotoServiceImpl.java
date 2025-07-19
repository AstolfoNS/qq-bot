package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.PhotoMapper;
import com.astolfo.robotservice.server.model.entity.PhotoEntity;
import com.astolfo.robotservice.server.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, PhotoEntity> implements PhotoService {
}
