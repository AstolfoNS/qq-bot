package com.astolfo.robotservice.server.service.impl;

import com.astolfo.robotservice.server.mapper.TagMapper;
import com.astolfo.robotservice.server.model.entity.TagEntity;
import com.astolfo.robotservice.server.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {
}
