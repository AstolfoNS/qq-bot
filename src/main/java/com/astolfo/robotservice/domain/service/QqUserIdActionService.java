package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QqUserIdActionService extends IService<QqUserIdActionEntity> {

    boolean hasPermission(String qqUserId, String actionName);

    int addPermission(String qqUserId, String actionName);

    int delPermission(String qqUserId, String actionName);

}
