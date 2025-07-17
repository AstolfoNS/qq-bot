package com.astolfo.robotservice.lolicon.service.impl;

import com.astolfo.robotservice.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.lolicon.service.LoliconService;
import com.astolfo.robotservice.lolicon.template.PhotoInfoTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoliconServiceImpl implements LoliconService {

    @Resource
    private LoliconClientApi loliconClientApi;

    @Resource
    private PhotoInfoTemplate photoInfoTemplate;


    @Override
    public Mono<Messages> processPhotoV1() {
        return loliconClientApi
                .getPhotoV1()
                .handle((response, sink) -> sink.next(photoInfoTemplate.toMessages(response.getData().getFirst())));
    }


    @Override
    public Mono<Messages> processPhotoV2() {
        return loliconClientApi
                .getPhotoV2()
                .handle((response, sink) -> {
                    try {
                        sink.next(photoInfoTemplate.toMessages(response.getData().getFirst()));
                    } catch (JsonProcessingException exception) {
                        sink.error(new RuntimeException(exception));
                    }
                });
    }
}