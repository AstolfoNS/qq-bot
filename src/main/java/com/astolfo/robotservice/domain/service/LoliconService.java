package com.astolfo.robotservice.domain.service;

import love.forte.simbot.message.Messages;
import reactor.core.publisher.Mono;

public interface LoliconService {

    Mono<Messages> processPicture(
            String r18,
            int number,
            String keyword
    );

}
