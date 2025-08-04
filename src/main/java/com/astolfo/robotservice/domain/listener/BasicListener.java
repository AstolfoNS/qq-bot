package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.infrastructure.common.utils.CommonUtil;
import com.astolfo.robotservice.infrastructure.common.utils.MessagesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import love.forte.simbot.resource.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BasicListener {

    @Resource
    private ObjectMapper objectMapper;

    @Filter("^/chat\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> chat(MessageEvent event) {
        for (Message.Element element : event.getMessageContent().getMessages()) {
            log.info("class: {}", element.getClass());
        }

        return event.replyAsync(Messages.of(MessagesUtil.removePrefix(event, "/chat ")));
    }

    @Filter("^/roll\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> roll(MessageEvent event) {
        List<Message.Element> inputElements = MessagesUtil.removePrefix(event, "/roll ");

        List<Message.Element> tokens = MessagesUtil.splitByWhitespace(inputElements);

        List<List<Message.Element>> options = MessagesUtil.groupByWhitespace(tokens);

        if (options.isEmpty()) {
            return event.replyAsync(Messages.of(Text.of("error: 无效的选项")));
        }

        return event.replyAsync(Messages.of(options.get((int) CommonUtil.randomFromZero(options.size() - 1))));
    }

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

            return event.replyAsync(String.valueOf(CommonUtil.random(min, max)));
        } catch (NumberFormatException exception) {
            return event.replyAsync("error: 输入数字过大无法计算");
        }
    }

    @Filter("^/logs")
    @Listener
    public CompletableFuture<?> logs(MessageEvent event) {
        return null;
    }
}
