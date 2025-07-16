package com.astolfo.robotservice.common.listener;

import com.astolfo.robotservice.common.infrastructure.utils.MessagesUtil;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class CommonListener {

    @Filter("^/chat\\s+(.*)")
    @Listener
    public CompletableFuture<?> chat(MessageEvent event) {
        List<Message.Element> elementList = MessagesUtil.analysisToElement(event);

        if (CollectionUtils.isEmpty(elementList) || !(elementList.getFirst() instanceof OneBotText.Element firstElement)) {
            return event.replyAsync("error");
        } else {
            elementList.set(0, Text.of(firstElement.getText().substring("/chat ".length())));

            return event.replyAsync(Messages.of(elementList));
        }
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

            return event.replyAsync(String.valueOf(ThreadLocalRandom.current().nextLong(min, max + 1)));
        } catch (NumberFormatException exception) {
            return event.replyAsync("/rand 输入数字过大无法计算");
        }
    }

}
