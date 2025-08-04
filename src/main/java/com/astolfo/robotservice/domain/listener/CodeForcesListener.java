package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.infrastructure.common.utils.CommonUtil;
import com.astolfo.robotservice.infrastructure.common.constants.CodeForcesConstant;
import com.astolfo.robotservice.domain.service.CodeForcesService;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CodeForcesListener {

    @Resource
    private CodeForcesService codeForcesService;


    @Filter("^/cf\\s+user{{checkHistoricHandles,(?:\\s+(?:--true|--false))?}}{{handles,(?:\\s+\\S+)+}}$")
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
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }

    @Filter("^/cf\\s+rating\\s+{{handle,(\\S+)}}(?:\\s+{{numberString,(\\d+)}})?")
    @Listener
    public CompletableFuture<?> getCodeForcesUserRatingHistory(
            MessageEvent event,
            @FilterValue("handle") String handle,
            @FilterValue(value = "numberString", required = false) String numberString
    ) {
        return codeForcesService
                .processUserRatingHistory(
                        handle,
                        Optional
                                .ofNullable(numberString)
                                .map(CommonUtil::praseIntElseMax)
                                .orElse(CodeForcesConstant.MAX_RATING_HISTORY_SIZE)
                )
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }
}
