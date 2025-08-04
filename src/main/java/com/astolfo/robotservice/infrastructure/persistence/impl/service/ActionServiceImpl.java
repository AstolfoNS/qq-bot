package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.ActionService;
import com.astolfo.robotservice.infrastructure.persistence.mapper.ActionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.ActionEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, ActionEntity> implements ActionService {
}
