package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.PictureTagMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.PictureTagEntity;
import com.astolfo.robotservice.domain.service.PictureTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PictureTagServiceImpl extends ServiceImpl<PictureTagMapper, PictureTagEntity> implements PictureTagService {
}
