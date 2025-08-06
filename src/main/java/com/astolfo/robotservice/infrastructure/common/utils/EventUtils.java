package com.astolfo.robotservice.infrastructure.common.utils;

import com.astolfo.robotservice.infrastructure.common.instances.WhitespaceElement;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EventUtils {

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
        return new ArrayList<>(EventUtils.analysisToMessages(event).toList());
    }

   public static List<Message.Element> removePrefix(MessageEvent event, String prefix) {
       List<Message.Element> elementList = EventUtils.analysisToElementList(event);

       elementList.set(0, Text.of(TextElementUtils.getText(elementList.getFirst()).substring(prefix.length())));

       return elementList;
   }

    public static List<Message.Element> splitByWhitespace(List<Message.Element> inputElementList) {
        return inputElementList
                .stream()
                .flatMap(element -> {
                    if (TextElementUtils.hasText(element)) {
                        return Arrays
                                .stream(TextElementUtils.getText(element).split("(?<=\\s)|(?=\\s+)"))
                                .map(token -> token.isBlank() ? WhitespaceElement.INSTANCE : Text.of(token));
                    }
                    return Stream.of(element);
                })
                .toList();
    }

    public static List<List<Message.Element>> groupByWhitespace(List<Message.Element> tokens) {
        List<List<Message.Element>> options = new ArrayList<>();
        List<Message.Element> currentOption = new ArrayList<>();

        for (Message.Element token : tokens) {
            if (token == WhitespaceElement.INSTANCE) {
                if (!currentOption.isEmpty()) {
                    options.add(currentOption);
                    currentOption = new ArrayList<>();
                }
            } else {
                currentOption.add(token);
            }
        }
        if (!currentOption.isEmpty()) {
            options.add(currentOption);
        }

        return options;
    }

    public static String getQqId(MessageEvent event) {
        return event.getId().toString().split("-")[0];
    }

    public static String getQqUserId(MessageEvent event) {
        return event.getAuthorId().toString();
    }

}
