package com.astolfo.robotservice;

import com.astolfo.robotservice.lolicon.service.LoliconService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class LoliconServiceTest {

    @Autowired
    private LoliconService loliconService;


    @Test
    public void test() {
    }
}
