package com.astolfo.robotservice.infrastructure.utils;

import love.forte.simbot.component.onebot.v11.message.segment.OneBotText;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;

public class TextElementUtil {

    public static String getText(Message.Element element) {
       if (element instanceof OneBotText.Element textElement) {
           return textElement.getText();
       }
       if (element instanceof Text textElement) {
           return textElement.getText();
       }

       return null;
    }

    public static boolean hasText(Message.Element element) {
        return  element instanceof OneBotText.Element || element instanceof Text;
    }

}
