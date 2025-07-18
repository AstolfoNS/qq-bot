package com.astolfo.robotservice.robot.lolicon.listener;

import com.astolfo.robotservice.robot.lolicon.service.LoliconService;
import jakarta.annotation.Resource;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class LoliconListener {

    @Resource
    private LoliconService loliconService;


    @Filter("^/setu\\s+v1")
    @Listener
    public CompletableFuture<?> getPhotoV1(MessageEvent event) {
        return loliconService
                .processPhotoV1()
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }

    @Filter("^/setu\\s+v2")
    @Listener
    public CompletableFuture<?> getPhotoV2(MessageEvent event) {
        return loliconService
                .processPhotoV2()
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }
}
