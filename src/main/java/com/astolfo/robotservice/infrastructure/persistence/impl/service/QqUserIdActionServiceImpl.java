package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.service.QqUserIdActionService;
import com.astolfo.robotservice.infrastructure.common.constants.RedisCacheConstant;
import com.astolfo.robotservice.infrastructure.common.utils.RedisCacheUtils;
import com.astolfo.robotservice.infrastructure.persistence.mapper.QqUserIdActionMapper;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class QqUserIdActionServiceImpl extends ServiceImpl<QqUserIdActionMapper, QqUserIdActionEntity> implements QqUserIdActionService {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Resource
    private QqUserIdActionMapper qqUserIdActionMapper;


    public List<QqUserIdActionEntity> updateCacheList(String qqUserId) {
        List<QqUserIdActionEntity> qqUserIdActionEntityList = qqUserIdActionMapper.selectList(
                Wrappers
                        .<QqUserIdActionEntity>lambdaQuery()
                        .eq(QqUserIdActionEntity::getQqUserId, qqUserId)
                        .orderByAsc(QqUserIdActionEntity::getActionName)
        );

        redisCacheUtils.delete(RedisCacheConstant.QQ_USER_ID_PREFIX.concat(qqUserId));
        redisCacheUtils.setList(RedisCacheConstant.QQ_USER_ID_PREFIX.concat(qqUserId), qqUserIdActionEntityList);

        return qqUserIdActionEntityList;
    }

    @Override
    public List<QqUserIdActionEntity> fetchListByQqUserId(String qqUserId) {
        List<QqUserIdActionEntity> qqUserIdActionEntityList = redisCacheUtils.getListOrNull(RedisCacheConstant.QQ_USER_ID_PREFIX.concat(qqUserId));

        return Objects.isNull(qqUserIdActionEntityList) ? this.updateCacheList(qqUserId) : qqUserIdActionEntityList;
    }

    @Override
    public boolean hasPermission(String qqUserId, String actionName) {
        return this
                .fetchListByQqUserId(qqUserId)
                .stream()
                .anyMatch(qqUserIdActionEntity -> actionName.equals(qqUserIdActionEntity.getActionName()));
    }

    @Transactional
    @Override
    public int addPermission(String qqUserId, String actionName) {
        int result = qqUserIdActionMapper.insert(
                QqUserIdActionEntity
                        .builder()
                        .qqUserId(qqUserId)
                        .actionName(actionName)
                        .build()
        );

        this.updateCacheList(qqUserId);

        return result;
    }

    @Transactional
    @Override
    public int delPermission(String qqUserId, String actionName) {
        int result = qqUserIdActionMapper.delete(
                Wrappers
                        .<QqUserIdActionEntity>lambdaQuery()
                        .eq(QqUserIdActionEntity::getQqUserId, qqUserId)
                        .eq(QqUserIdActionEntity::getActionName, actionName)
        );

        this.updateCacheList(qqUserId);

        return result;
    }

}
