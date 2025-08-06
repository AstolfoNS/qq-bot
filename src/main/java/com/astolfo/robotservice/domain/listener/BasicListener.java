package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.domain.service.*;
import com.astolfo.robotservice.infrastructure.common.annotations.Action;
import com.astolfo.robotservice.infrastructure.common.utils.CommonUtils;
import com.astolfo.robotservice.infrastructure.common.utils.EventUtils;
import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BasicListener {

    @Resource
    private MailService mailService;

    @Resource
    private UserService userService;


    @Action("Basic.chat()")
    @Filter("^/chat\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> chat(MessageEvent event) {
        for (Message.Element element : event.getMessageContent().getMessages()) {
            log.info("class: {}", element.getClass());
        }

        return event.replyAsync(Messages.of(EventUtils.removePrefix(event, "/chat ")));
    }

    @Action("Basic.roll()")
    @Filter("^/roll\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> roll(MessageEvent event) {
        List<Message.Element> inputElements = EventUtils.removePrefix(event, "/roll ");

        List<Message.Element> tokens = EventUtils.splitByWhitespace(inputElements);

        List<List<Message.Element>> options = EventUtils.groupByWhitespace(tokens);

        if (options.isEmpty()) {
            return event.replyAsync(Messages.of(Text.of("error: 无效的选项。")));
        }

        return event.replyAsync(Messages.of(options.get((int) CommonUtils.randomFromZero(options.size() - 1))));
    }

    @Action("basic.rand()")
    @Filter("^/rand\\s+{{stringNumber1,(\\d+)}}\\s+{{stringNumber2,(\\d+)}}")
    @Listener
    public CompletableFuture<?> rand(
            MessageEvent event,
            @FilterValue("stringNumber1") String stringNumber1,
            @FilterValue("stringNumber2") String stringNumber2
    ) {
        try {
            long number1 = Long.parseLong(stringNumber1);
            long number2 = Long.parseLong(stringNumber2);

            long min = Math.min(number1, number2);
            long max = Math.max(number1, number2);

            return event.replyAsync(String.valueOf(CommonUtils.random(min, max)));
        } catch (NumberFormatException exception) {
            return event.replyAsync("error: 输入数字过大无法计算。");
        }
    }

    @Action("Basic.logs()")
    @Filter("^/logs")
    @Listener
    public CompletableFuture<?> logs(MessageEvent event) {
        String qqNumber = event.getAuthorId().toString();

        UserEntity userEntity = userService.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, qqNumber));

        if (Objects.isNull(userEntity)) {
            return event.replyAsync("没有查询到账户信息，请使用qq号注册bot再尝试。");
        }

        try {
            mailService.sendLogFileToEmail(userEntity.getQqEmail());

            return event.replyAsync("日志已成功发送至邮箱！");
        } catch (Exception exception) {
            return event.replyAsync("日志发送失败，请检查网络是否有问题。");
        }
    }

}
