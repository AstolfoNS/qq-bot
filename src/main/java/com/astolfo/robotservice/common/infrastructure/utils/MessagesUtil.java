package com.astolfo.robotservice.common.infrastructure.utils;

import love.forte.simbot.message.Messages;

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
}
