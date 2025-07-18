package com.astolfo.robotservice;

import com.astolfo.robotservice.bot.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.bot.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.bot.codeforces.model.dto.RatingHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CodeForcesClientApiTest {

    @Autowired
    private CodeForcesClientApi codeForcesClientApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {
        CodeForcesResponse<RatingHistory> response = codeForcesClientApi.getUserRatingHistory("AstolfoNS").block();

        log.info("response = {}", response);

        log.info("response = {}", objectMapper.writeValueAsString(response));
    }

}
