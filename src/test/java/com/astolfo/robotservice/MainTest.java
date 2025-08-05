package com.astolfo.robotservice;

import com.astolfo.robotservice.domain.service.MailService;
import com.astolfo.robotservice.domain.service.QqIdActionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MainTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private QqIdActionService qqIdActionService;


    @Test
    public void test() {
//        mailService.sendLogFileToEmail("1780884916@qq.com");
    }

}
