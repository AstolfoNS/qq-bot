package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.PictureTagMapper;
import com.astolfo.robotservice.server.model.entity.PictureTagEntity;
import com.astolfo.robotservice.server.service.PictureTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PictureTagServiceImpl extends ServiceImpl<PictureTagMapper, PictureTagEntity> implements PictureTagService {
}
