package com.astolfo.robotservice.robot.lolicon.service;

import love.forte.simbot.message.Messages;
import reactor.core.publisher.Mono;

public interface LoliconService {

    Mono<Messages> processPhotoV1();

    Mono<Messages> processPhotoV2();
}
