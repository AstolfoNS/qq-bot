package com.astolfo.robotservice.infrastructure.common.instances;

import lombok.Data;
import love.forte.simbot.message.Message;

@Data
public class WhitespaceElement implements Message.Element {

    public static final WhitespaceElement INSTANCE = new WhitespaceElement();

}
