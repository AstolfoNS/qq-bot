package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QqIdActionService extends IService<QqIdActionEntity> {

    boolean hasPermission(String qqId, String actionName);

    int addPermission(String qqId, String actionName);

    int delPermission(String qqId, String actionName);
}
