package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.QqUserIdActionService;
import com.astolfo.robotservice.infrastructure.persistence.mapper.QqUserIdActionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class QqUserIdActionServiceImpl extends ServiceImpl<QqUserIdActionMapper, QqUserIdActionEntity> implements QqUserIdActionService {

    @Resource
    private QqUserIdActionMapper qqUserIdActionMapper;


    @Override
    public boolean hasPermission(String qqUserId, String actionName) {
        return qqUserIdActionMapper.exists(
                Wrappers
                        .<QqUserIdActionEntity>lambdaQuery()
                        .eq(QqUserIdActionEntity::getQqUserId, qqUserId)
                        .eq(QqUserIdActionEntity::getAction, actionName)
        );
    }

    @Override
    public int addPermission(String qqUserId, String actionName) {
        return qqUserIdActionMapper.insert(
                QqUserIdActionEntity
                        .builder()
                        .qqUserId(qqUserId)
                        .action(actionName)
                        .build()
        );
    }

    @Override
    public int delPermission(String qqUserId, String actionName) {
        return qqUserIdActionMapper.delete(
                Wrappers
                        .<QqUserIdActionEntity>lambdaQuery()
                        .eq(QqUserIdActionEntity::getQqUserId, qqUserId)
                        .eq(QqUserIdActionEntity::getAction, actionName)
        );
    }

}
