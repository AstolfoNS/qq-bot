package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.PhotoTagMapper;
import com.astolfo.robotservice.server.model.entity.PhotoTagEntity;
import com.astolfo.robotservice.server.service.PhotoTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PhotoTagServiceImpl extends ServiceImpl<PhotoTagMapper, PhotoTagEntity> implements PhotoTagService {
}
