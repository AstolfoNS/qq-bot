package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.PictureMapper;
import com.astolfo.robotservice.server.model.entity.PictureEntity;
import com.astolfo.robotservice.server.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, PictureEntity> implements PictureService {
}
