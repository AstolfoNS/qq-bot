package com.astolfo.robotservice.robot.lolicon.service.impl;

import com.astolfo.robotservice.robot.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.robot.lolicon.service.LoliconService;
import com.astolfo.robotservice.robot.lolicon.model.template.PhotoInfoTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class LoliconServiceImpl implements LoliconService {

    @Resource
    private LoliconClientApi loliconClientApi;

    @Resource
    private PhotoInfoTemplate photoInfoTemplate;


    @Override
    public Mono<Messages> processPhotoV2() {
        return loliconClientApi
                .getPhotoV2()
                .handle((response, sink) -> {
                    try {
                        log.info("processPhotoV2:response = {}", response);

                        sink.next(photoInfoTemplate.toMessages(response.getData().getFirst()));
                    } catch (JsonProcessingException exception) {
                        sink.error(new RuntimeException(exception));
                    }
                });
    }
}