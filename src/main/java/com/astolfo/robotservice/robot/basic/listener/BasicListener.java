package com.astolfo.robotservice.robot.basic.listener;

import com.astolfo.robotservice.infrastructure.utils.CommonUtil;
import com.astolfo.robotservice.infrastructure.utils.MessagesUtil;
import com.astolfo.robotservice.infrastructure.utils.TextElementUtil;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.component.onebot.v11.message.segment.OneBotText;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BasicListener {

    private static final Object WHITESPACE_SEPARATOR = new Object();


    @Filter("^/chat\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> chat(MessageEvent event) {
        return event.replyAsync(Messages.of(MessagesUtil.removePrefix(event, "/chat ")));
    }

    @Filter("^/roll\\s+[\\s\\S]*")
    @Listener
    public CompletableFuture<?> roll(MessageEvent event) {
        List<Message.Element> inputElements = MessagesUtil.removePrefix(event, "/roll ");

        List<List<Message.Element>> options = new ArrayList<>();
        List<Message.Element> currentOption = new ArrayList<>();

        List<Object> tokens = inputElements
                .stream()
                .flatMap(element -> {
                    if (TextElementUtil.hasText(element)) {
                        return Arrays
                                .stream(TextElementUtil.getText(element).split("(?<=\\s)|(?=\\s+)"))
                                .map(token -> token.isBlank() ? WHITESPACE_SEPARATOR : Text.of(token));
                    } else {
                        return Stream.of(element);
                    }
                })
                .toList();

        for (Object token : tokens) {
            if (token == WHITESPACE_SEPARATOR) {
                if (!currentOption.isEmpty()) {
                    options.add(currentOption);
                    currentOption = new ArrayList<>();
                }
            } else {
                currentOption.add((Message.Element) token);
            }
        }
        if (!currentOption.isEmpty()) {
            options.add(currentOption);
        }
        if (options.isEmpty()) {
            return event.replyAsync(Messages.of(Text.of("error: 无效的选项")));
        }

        return event.replyAsync(Messages.of(options.get((int) CommonUtil.random(0, options.size() - 1))));
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
}
