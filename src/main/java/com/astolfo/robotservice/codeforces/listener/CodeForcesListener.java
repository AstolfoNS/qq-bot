package com.astolfo.robotservice.codeforces.listener;

import com.astolfo.robotservice.common.infrastructure.utils.ErrorMessage;
import com.astolfo.robotservice.codeforces.service.CodeForcesService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CodeForcesListener {

    @Resource
    private CodeForcesService codeForcesService;


    @Filter("^/cf\\s+user{{checkHistoricHandles,(?:\\s+(?:--true|--false))?}}{{handles,(?:\\s+\\w+)+}}$")
    @Listener
    public CompletableFuture<?> getCodeForcesUserInfo(
            MessageEvent event,
            @FilterValue(value = "checkHistoricHandles", required = false) String checkHistoricHandles,
            @FilterValue("handles") String handles
    ) {
        return codeForcesService
                .processUserInfo(
                        Arrays
                                .stream(handles.split("\\s+"))
                                .filter(StringUtils::isNotEmpty)
                                .distinct()
                                .toList(),
                        "--true".equals(checkHistoricHandles.replaceAll("\\s", ""))
                )
                .flatMapMany(Flux::fromIterable)
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(ErrorMessage.info("CodeForcesListener:getCodeForcesUserInfo", exception.getMessage())))
                        .then())
                .toFuture();
    }

    @Filter("^/cf\\s+rating\\s+{{handle,(\\w+)}}\\s+{{numberString,(\\d+)}}")
    @Listener
    public CompletableFuture<?> getCodeForcesUserRatingHistory(
            MessageEvent event,
            @FilterValue("handle") String handle,
            @FilterValue("numberString") String numberString
    ) {
        int number;

        try {
            number = Integer.parseInt(numberString);
        } catch (NumberFormatException exception) {
            number = Integer.MAX_VALUE;
        }

        return codeForcesService
                .processUserRatingHistory(handle, number)
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(ErrorMessage.info("CodeForcesListener:getCodeForcesUserRatingHistory", exception.getMessage())))
                        .then())
                .toFuture();
    }
}
