package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.persistence.mapper.TagMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.TagEntity;
import com.astolfo.robotservice.domain.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {
}
