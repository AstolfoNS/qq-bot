package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.QqIdActionService;
import com.astolfo.robotservice.infrastructure.persistence.mapper.QqIdActionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class QqIdActionServiceImpl extends ServiceImpl<QqIdActionMapper, QqIdActionEntity> implements QqIdActionService {

    @Resource
    private QqIdActionMapper qqIdActionMapper;


    @Override
    public boolean hasPermission(String qqId, String actionName) {
        return qqIdActionMapper.exists(
                Wrappers
                        .<QqIdActionEntity>lambdaQuery()
                        .eq(QqIdActionEntity::getQqId, qqId)
                        .eq(QqIdActionEntity::getActionName, actionName)
                        .or()
                        .eq(QqIdActionEntity::getIsCommon, true)
                        .eq(QqIdActionEntity::getActionName, actionName)
        );
    }

    @Override
    public int addPermission(String qqId, String actionName) {
        return qqIdActionMapper.insert(
                QqIdActionEntity
                        .builder()
                        .qqId(qqId)
                        .actionName(actionName)
                        .build()
        );
    }

    @Override
    public int delPermission(String qqId, String actionName) {
        return qqIdActionMapper.delete(
                Wrappers
                        .<QqIdActionEntity>lambdaQuery()
                        .eq(QqIdActionEntity::getQqId, qqId)
                        .eq(QqIdActionEntity::getActionName, actionName)
        );
    }

}
