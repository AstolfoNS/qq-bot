package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.QqIdActionService;
import com.astolfo.robotservice.infrastructure.common.constants.RedisCacheConstant;
import com.astolfo.robotservice.infrastructure.common.utils.RedisCacheUtils;
import com.astolfo.robotservice.infrastructure.persistence.mapper.QqIdActionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class QqIdActionServiceImpl extends ServiceImpl<QqIdActionMapper, QqIdActionEntity> implements QqIdActionService {

    @Resource
    private QqIdActionMapper qqIdActionMapper;

    @Resource
    private RedisCacheUtils redisCacheUtils;


    public List<QqIdActionEntity> updateCacheList(String qqId) {
        List<QqIdActionEntity> qqIdActionEntityList = qqIdActionMapper.selectList(
                Wrappers
                        .<QqIdActionEntity>lambdaQuery()
                        .eq(QqIdActionEntity::getQqId, qqId)
                        .orderByAsc(QqIdActionEntity::getActionName)
            );

        redisCacheUtils.delete(RedisCacheConstant.QQ_ID_PREFIX.concat(qqId));
        redisCacheUtils.setList(RedisCacheConstant.QQ_ID_PREFIX.concat(qqId), qqIdActionEntityList);

        return qqIdActionEntityList;
    }

    @Override
    public List<QqIdActionEntity> fetchListByQqId(String qqId) {
        List<QqIdActionEntity> qqIdActionEntityList = redisCacheUtils.getListOrNull(RedisCacheConstant.QQ_ID_PREFIX.concat(qqId));

        return Objects.isNull(qqIdActionEntityList) ? this.updateCacheList(qqId) : qqIdActionEntityList;
    }

    @Override
    public boolean hasPermission(String qqId, String actionName) {
        return this
                .fetchListByQqId(qqId)
                .stream()
                .anyMatch(qqIdActionEntity -> actionName.equals(qqIdActionEntity.getActionName()));
    }

    @Transactional
    @Override
    public int addPermission(String qqId, String actionName) {
        int result = qqIdActionMapper.insert(
                QqIdActionEntity
                        .builder()
                        .qqId(qqId)
                        .actionName(actionName)
                        .build()
        );

        this.updateCacheList(qqId);

        return result;
    }

    @Transactional
    @Override
    public int delPermission(String qqId, String actionName) {
        int result = qqIdActionMapper.delete(
                Wrappers
                        .<QqIdActionEntity>lambdaQuery()
                        .eq(QqIdActionEntity::getQqId, qqId)
                        .eq(QqIdActionEntity::getActionName, actionName)
        );

        this.updateCacheList(qqId);

        return result;
    }

}
