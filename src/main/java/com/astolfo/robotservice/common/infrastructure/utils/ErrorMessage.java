package com.astolfo.robotservice.common.infrastructure.utils;

import com.astolfo.robotservice.common.service.template.StringTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import love.forte.simbot.message.Messages;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {

    private String event;

    private String message;


    public static String info(String event, String message) {
        return String.format("""
                {
                    "event": "%s",
                    "error": "%s"
                }
                """, event, message);
    }

    public static Messages toMessages(String event, String messages) {
        return StringTemplate.toMessages(ErrorMessage.info(event, messages));
    }
}
