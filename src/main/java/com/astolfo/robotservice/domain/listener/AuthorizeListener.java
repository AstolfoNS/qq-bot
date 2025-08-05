package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.domain.service.ActionService;
import com.astolfo.robotservice.domain.service.QqIdActionService;
import com.astolfo.robotservice.domain.service.QqUserIdActionService;
import com.astolfo.robotservice.infrastructure.common.annotations.Action;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.ActionEntity;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AuthorizeListener {

    @Resource
    private ActionService actionService;

    @Resource
    private QqIdActionService qqIdActionService;

    @Resource
    private QqUserIdActionService qqUserIdActionService;


    @Action("Auth.list()")
    @Filter("^/auth\\s+--list\\s+{{object,(--person|--group)}}\\s+{{number,(\\d+)}}")
    @Listener
    public CompletableFuture<?> fetchList(
            MessageEvent event,
            @FilterValue("object") String object,
            @FilterValue("number") String number
    ) {
        if ("--person".equals(object)) {
            return event.replyAsync(Messages.of(
                    qqUserIdActionService
                            .list(
                                    Wrappers
                                            .<QqUserIdActionEntity>lambdaQuery()
                                            .eq(QqUserIdActionEntity::getQqUserId, number)
                                            .or()
                                            .eq(QqUserIdActionEntity::getIsCommon, true)
                                            .orderByAsc(QqUserIdActionEntity::getActionName)
                            )
                            .stream()
                            .map(qqUserIdActionEntity -> Text.of(String.format("\n%s", qqUserIdActionEntity.getActionName())))
                            .toList()
            ));
        }
        if ("--group".equals(object)) {
            return event.replyAsync(Messages.of(
                    qqIdActionService
                            .list(
                                    Wrappers
                                            .<QqIdActionEntity>lambdaQuery()
                                            .eq(QqIdActionEntity::getQqId, number)
                                            .or()
                                            .eq(QqIdActionEntity::getIsCommon, true)
                                            .orderByAsc(QqIdActionEntity::getActionName)
                            )
                            .stream()
                            .map(qqIdActionEntity -> Text.of(String.format("\n%s", qqIdActionEntity.getActionName())))
                            .toList()
            ));
        }

        return event.replyAsync("未知错误！");
    }

    @Action("Auth.auth()")
    @Filter("^/auth\\s+{{object,(--person|--group)}}\\s+{{number,(\\d+)}}\\s+{{operation,(add|del)}}\\s+{{actionName,(.+)}}")
    @Listener
    public CompletableFuture<?> authorize(
            MessageEvent event,
            @FilterValue("object") String object,
            @FilterValue("number") String number,
            @FilterValue("operation") String operation,
            @FilterValue("actionName") String actionName
    ) {
        if (!actionService.exists(Wrappers.<ActionEntity>lambdaQuery().eq(ActionEntity::getActionName, actionName))) {
            return event.replyAsync(actionName + " 行为不存在！");
        }

        if ("--person".equals(object)) {
            if ("del".equals(operation) && qqUserIdActionService.delPermission(number, actionName) > 0) {
                return event.replyAsync("弃权成功！");
            }
            if (qqUserIdActionService.hasPermission(number, actionName)) {
                return event.replyAsync(number + " qq用户具备 " + actionName + " 行为权限");
            }
            if ("add".equals(operation) && qqUserIdActionService.addPermission(number, actionName) > 0) {
                return event.replyAsync("授权成功！");
            }
        }
        if ("--group".equals(object)) {
            if ("del".equals(operation) && qqIdActionService.delPermission(number, actionName) > 0) {
                return event.replyAsync("弃权成功！");
            }
            if (qqIdActionService.hasPermission(number, actionName)) {
                return event.replyAsync(number + " qq群已具备 " + actionName + " 行为权限");
            }
            if ("add".equals(operation) && qqIdActionService.addPermission(number, actionName) > 0) {
                return event.replyAsync("授权成功！");
            }
        }

        return event.replyAsync("操作失败！");
    }

}
