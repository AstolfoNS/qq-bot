package com.astolfo.robotservice.infrastructure.utils;

import love.forte.simbot.component.onebot.v11.message.segment.OneBotText;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

    public static List<Message.Element> analysisToElementList(MessageEvent event) {
        return new ArrayList<>(MessagesUtil.analysisToMessages(event).toList());
    }

   public static List<Message.Element> removePrefix(MessageEvent event, String prefix) {
       List<Message.Element> elementList = MessagesUtil.analysisToElementList(event);

       elementList.set(0, Text.of(TextElementUtil.getText(elementList.getFirst()).substring(prefix.length())));

       return elementList;
   }
}
