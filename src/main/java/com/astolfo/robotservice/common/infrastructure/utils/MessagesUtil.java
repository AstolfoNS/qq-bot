package com.astolfo.robotservice.common.infrastructure.utils;

import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MessagesUtil {

    public static Messages merge(Messages... messages) {
        return Messages.of(
                Stream
                        .of(messages)
                        .flatMap(m -> m.toList().stream())
                        .toList()
        );
    }

    public static Messages analysisToMessages(MessageEvent event) {
        return event.getMessageContent().getMessages();
    }

    public static List<Message.Element> analysisToElement(MessageEvent event) {
        return new ArrayList<>(MessagesUtil.analysisToMessages(event).toList());
    }
}
