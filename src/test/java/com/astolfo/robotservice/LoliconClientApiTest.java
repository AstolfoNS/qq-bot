package com.astolfo.robotservice;

import com.astolfo.robotservice.lolicon.api.LoliconClientApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class LoliconClientApiTest {

    @Autowired
    private LoliconClientApi loliconClientApi;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void test() throws JsonProcessingException {
    }
}
