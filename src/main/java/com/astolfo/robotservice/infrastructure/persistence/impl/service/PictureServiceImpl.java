package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.PictureMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.PictureEntity;
import com.astolfo.robotservice.domain.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, PictureEntity> implements PictureService {
}
