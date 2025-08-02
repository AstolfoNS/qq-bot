package com.astolfo.robotservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class MainTest {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    public void test() {
        String ans = passwordEncoder.encode("LuoXiaoBin");

        log.info("ans: {}", ans);

        boolean isRight = passwordEncoder.matches("LuoXiaoBin", ans);

        log.info("isRight :{}", isRight);

    }

}
