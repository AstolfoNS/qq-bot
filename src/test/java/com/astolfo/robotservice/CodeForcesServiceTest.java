package com.astolfo.robotservice;

import com.astolfo.robotservice.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.robot.codeforces.service.CodeForcesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CodeForcesServiceTest {

    @Autowired
    private CodeForcesService codeForcesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TimeConverter timeConverter;


    @Test
    public void test() throws JsonProcessingException {

    }
}
