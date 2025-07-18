package com.astolfo.robotservice;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MainTest {

    @Test
    public void Test() {
        log.info("class: {}", Messages.class);

        log.info("class: {}", MessageEvent.class);
    }

}
