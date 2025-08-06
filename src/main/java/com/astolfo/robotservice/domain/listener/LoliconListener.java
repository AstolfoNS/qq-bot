package com.astolfo.robotservice.domain.listener;

import com.astolfo.robotservice.infrastructure.common.annotations.Action;
import com.astolfo.robotservice.infrastructure.common.utils.CommonUtils;
import com.astolfo.robotservice.infrastructure.common.enums.R18Enum;
import com.astolfo.robotservice.domain.service.LoliconService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class LoliconListener {

    @Resource
    private LoliconService loliconService;


    @Action("Lolicon.getPicture()")
    @Filter("^/setu(?:\\s+{{r18,(?:--rand|--true|--false)}})?(?:\\s+{{num,(?:\\d+)}})?(?:\\s+{{keyword,(.+)}})?")
    @Listener
    public CompletableFuture<?> getPicture(
            MessageEvent event,
            @FilterValue(value = "r18", required = false) String r18,
            @FilterValue(value = "num", required = false) String num,
            @FilterValue(value = "keyword", required = false) String keyword
    ) {
        return loliconService
                .processPicture(
                        R18Enum.typeOf(r18).getValue(),
                        Optional
                                .ofNullable(num)
                                .map(CommonUtils::praseIntElseMax)
                                .orElse(1),
                        Optional
                                .ofNullable(keyword)
                                .orElse("")
                )
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }
}
