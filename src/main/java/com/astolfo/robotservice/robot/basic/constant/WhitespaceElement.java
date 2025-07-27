package com.astolfo.robotservice.robot.basic.constant;

import lombok.Data;
import love.forte.simbot.message.Message;

@Data
public class WhitespaceElement implements Message.Element {

    public static final WhitespaceElement INSTANCE = new WhitespaceElement();

}
