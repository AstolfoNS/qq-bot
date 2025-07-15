package com.astolfo.robotservice.common.service.template;

import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

@Component
public class StringTemplate {

    public static Messages toMessages(String message) {
        return Messages.of(Text.of(message));
    }
}
