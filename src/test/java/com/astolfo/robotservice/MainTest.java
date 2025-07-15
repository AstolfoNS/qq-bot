package com.astolfo.robotservice;

import com.astolfo.robotservice.common.infrastructure.utils.MessagesUtil;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MainTest {

    @Test
    public void Test() {
        Messages a = Messages.of(Text.of("a"));

        Messages b = Messages.of(Text.of("b"));

        Messages c = MessagesUtil.merge(a, b);

        c.toList().forEach(System.out::println);
    }

}
