package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface QqIdActionService extends IService<QqIdActionEntity> {

    List<QqIdActionEntity> fetchListByQqId(String qqId);

    boolean hasPermission(String qqId, String actionName);

    int addPermission(String qqId, String actionName);

    int delPermission(String qqId, String actionName);
}
