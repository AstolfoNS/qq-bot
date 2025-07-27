package com.astolfo.robotservice;

import com.astolfo.robotservice.infrastructure.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MainTest {

    @Test
    public void test() {

        for (int i = 0; i < 10; i++) {
            log.info("{} th, random: {}", i + 1, CommonUtil.randomFromZero(2));
        }

    }

}
