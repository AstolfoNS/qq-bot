package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface QqUserIdActionService extends IService<QqUserIdActionEntity> {

    List<QqUserIdActionEntity> fetchListByQqUserId(String qqUserId);

    boolean hasPermission(String qqUserId, String actionName);

    int addPermission(String qqUserId, String actionName);

    int delPermission(String qqUserId, String actionName);

}
