package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.domain.service.ActionService;
import com.astolfo.robotservice.domain.service.QqIdActionService;
import com.astolfo.robotservice.domain.service.QqUserIdActionService;
import com.astolfo.robotservice.infrastructure.common.annotations.Action;
import com.astolfo.robotservice.infrastructure.common.utils.EventUtils;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.ActionEntity;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqIdActionEntity;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.QqUserIdActionEntity;
import com.astolfo.robotservice.infrastructure.persistence.template.StringTemplate;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
@Component
public class AuthorizeListener {

    @Resource
    private ActionService actionService;

    @Resource
    private QqIdActionService qqIdActionService;

    @Resource
    private QqUserIdActionService qqUserIdActionService;


    public Messages getAuthListMessagesByQqUserId(String qqUserId) {
        return this.createAuthListMessages(qqUserId, qqUserIdActionService.fetchListByQqUserId(qqUserId), QqUserIdActionEntity::getActionName);
    }

    public Messages getAuthListMessagesByQqId(String qqId) {
        return this.createAuthListMessages(qqId, qqIdActionService.fetchListByQqId(qqId), QqIdActionEntity::getActionName);
    }

    private <T> Messages createAuthListMessages(String id, List<T> entityList, Function<T, String> actionNameMapper) {
        Messages context;

        if (entityList.isEmpty()) {
            context = StringTemplate.toMessages("auth empty!!! (>_<)");
        } else {
            context = Messages.of(
                    entityList
                            .stream()
                            .map(actionNameMapper)
                            .map(actionName -> Text.of("\n - " + actionName))
                            .toList()
            );
        }

        return EventUtils.merge(StringTemplate.toMessages(id + " auth list: "), context);
    }


    @Action("Auth.list()")
    @Filter("^/auth\\s+{{object,(--person|--group)}}(?:\\s+{{number,(?:\\d+)}})?")
    @Listener
    public CompletableFuture<?> fetchList(
            MessageEvent event,
            @FilterValue("object") String object,
            @FilterValue(value = "number", required = false) String number
    ) {
        if ("--person".equals(object)) {
            return event.replyAsync(this.getAuthListMessagesByQqUserId(Optional.ofNullable(number).orElse(EventUtils.getQqUserId(event))));
        }
        if ("--group".equals(object)) {
            return event.replyAsync(this.getAuthListMessagesByQqId(Optional.ofNullable(number).orElse(EventUtils.getQqId(event))));
        }

        return event.replyAsync("未知错误！");
    }

    @Action("Auth.auth()")
    @Filter("^/auth\\s+{{object,(--person|--group)}}(?:\\s+{{number,(?:\\d+)}})?\\s+{{operation,(add|del)}}\\s+{{actionName,(.+)}}")
    @Listener
    public CompletableFuture<?> authorize(
            MessageEvent event,
            @FilterValue("object") String object,
            @FilterValue(value = "number", required = false) String number,
            @FilterValue("operation") String operation,
            @FilterValue("actionName") String actionName
    ) {
        if (!actionService.exists(Wrappers.<ActionEntity>lambdaQuery().eq(ActionEntity::getActionName, actionName))) {
            return event.replyAsync(actionName + " 行为不存在！");
        }

        if ("--person".equals(object)) {
            if (Objects.isNull(number)) {
                number = EventUtils.getQqUserId(event);
            }

            if ("del".equals(operation) && qqUserIdActionService.delPermission(number, actionName) > 0) {
                return event.replyAsync("弃权成功！");
            }
            if (qqUserIdActionService.hasPermission(number, actionName)) {
                return event.replyAsync(number + " qq用户已具备 " + actionName + " 行为权限。");
            }
            if ("add".equals(operation) && qqUserIdActionService.addPermission(number, actionName) > 0) {
                return event.replyAsync("授权成功！");
            }
        }
        if ("--group".equals(object)) {
            if (Objects.isNull(number)) {
                number = EventUtils.getQqId(event);
            }

            if ("del".equals(operation) && qqIdActionService.delPermission(number, actionName) > 0) {
                return event.replyAsync("弃权成功！");
            }
            if (qqIdActionService.hasPermission(number, actionName)) {
                return event.replyAsync(number + " qq群组已具备 " + actionName + " 行为权限。");
            }
            if ("add".equals(operation) && qqIdActionService.addPermission(number, actionName) > 0) {
                return event.replyAsync("授权成功！");
            }
        }

        return event.replyAsync("操作失败！");
    }

}
