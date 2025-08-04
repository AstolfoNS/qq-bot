package com.astolfo.robotservice.infrastructure.common.aspects;

import com.astolfo.robotservice.domain.service.QqIdActionService;
import com.astolfo.robotservice.domain.service.QqUserIdActionService;
import com.astolfo.robotservice.infrastructure.common.annotations.Action;
import com.astolfo.robotservice.infrastructure.common.utils.EventUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ActionAspect {

    @Resource
    private QqIdActionService qqIdActionService;

    @Resource
    private QqUserIdActionService qqUserIdActionService;


    private boolean hasPermission(MessageEvent event, String action) {
        return qqIdActionService.hasPermission(EventUtil.getQqId(event), action) || qqUserIdActionService.hasPermission(EventUtil.getQqUserId(event), action);
    }

    @Around("@annotation(action) && args(event, ..)")
    public Object actionMethods(ProceedingJoinPoint joinPoint, Action action, MessageEvent event) throws Throwable {
        if (this.hasPermission(event, action.value())) {
            return joinPoint.proceed();
        }

        log.warn("QQId {} 或 QQUserId {} 没有执行动作 {} 的权限，操作已跳过。", EventUtil.getQqId(event), EventUtil.getQqUserId(event), action.value());

        return event.replyAsync("没有权限！");
    }

}
