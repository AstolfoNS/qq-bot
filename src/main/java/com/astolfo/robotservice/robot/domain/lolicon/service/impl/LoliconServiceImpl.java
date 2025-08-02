package com.astolfo.robotservice.robot.domain.lolicon.service.impl;

import com.astolfo.robotservice.robot.domain.basic.constant.LoliconConstant;
import com.astolfo.robotservice.robot.domain.basic.template.StringTemplate;
import com.astolfo.robotservice.robot.domain.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.robot.domain.lolicon.service.LoliconService;
import com.astolfo.robotservice.robot.domain.lolicon.model.template.PhotoInfoTemplate;
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
    public Mono<Messages> processPhoto(
            String r18,
            int number,
            String keyword
    ) {
        if (number > LoliconConstant.MAX_PHOTO_SIZE) {
            return Mono.just(StringTemplate.toMessages(String.format("最多只能查询%d张图片 >_<", LoliconConstant.MAX_PHOTO_SIZE)));
        }

        return loliconClientApi
                .getPhoto(r18, number, keyword)
                .handle((response, sink) -> {
                    try {
                        sink.next(photoInfoTemplate.toMessages(response.getData()));
                    } catch (JsonProcessingException exception) {
                        sink.error(new RuntimeException(exception));
                    }
                });
    }
}