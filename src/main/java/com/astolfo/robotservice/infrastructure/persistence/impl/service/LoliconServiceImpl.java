package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.infrastructure.common.constants.LoliconConstant;
import com.astolfo.robotservice.infrastructure.persistence.template.StringTemplate;
import com.astolfo.robotservice.domain.api.LoliconClientApi;
import com.astolfo.robotservice.domain.service.LoliconService;
import com.astolfo.robotservice.infrastructure.persistence.template.LoliconPictureInfoTemplate;
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
    private LoliconPictureInfoTemplate loliconPictureInfoTemplate;


    @Override
    public Mono<Messages> processPicture(
            String r18,
            int number,
            String keyword
    ) {
        if (number > LoliconConstant.MAX_PHOTO_SIZE) {
            return Mono.just(StringTemplate.toMessages(String.format("最多只能查询%d张图片 >_<", LoliconConstant.MAX_PHOTO_SIZE)));
        }

        return loliconClientApi
                .getPicture(r18, number, keyword)
                .handle((response, sink) -> {
                    try {
                        sink.next(loliconPictureInfoTemplate.toMessages(response.getData()));
                    } catch (JsonProcessingException exception) {
                        sink.error(new RuntimeException(exception));
                    }
                });
    }
}