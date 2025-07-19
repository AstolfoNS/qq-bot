package com.astolfo.robotservice.robot.lolicon.service;

import love.forte.simbot.message.Messages;
import reactor.core.publisher.Mono;

public interface LoliconService {

    Mono<Messages> processPhoto(
            String r18,
            int number,
            String tag
    );

}
