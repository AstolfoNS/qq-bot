package com.astolfo.robotservice.robot.domain.lolicon.listener;

import com.astolfo.robotservice.robot.domain.lolicon.service.LoliconService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.FilterValue;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class LoliconListener {

    @Resource
    private LoliconService loliconService;


    @Filter("^/setu(?:\\s+{{r18,(?:n|y|r)}})?(?:\\s+{{num,(?:\\d+)}})?(?:\\s+{{tag,(.+)}})?")
    @Listener
    public CompletableFuture<?> getPhoto(
            MessageEvent event,
            @FilterValue(value = "r18", required = false) String r18,
            @FilterValue(value = "num", required = false) String num,
            @FilterValue(value = "tag", required = false) String tag
    ) {
        String r18Param = "2";

        if (Objects.nonNull(r18)) {
            if (r18.equals("n")) {
                r18Param = "0";
            }
            if (r18.equals("y")) {
                r18Param = "1";
            }
        }

        int number;

        try {
            number = Integer.parseInt(num);
        } catch (NumberFormatException exception) {
            number = Integer.MAX_VALUE;
        }

        if (Objects.isNull(num)) {
            number = 1;
        }

        if (Objects.isNull(tag)) {
            tag = "";
        }

        return loliconService
                .processPhoto(r18Param, number, tag)
                .flatMap(messages -> Mono.fromFuture(event.replyAsync(messages)))
                .then()
                .onErrorResume(exception -> Mono
                        .fromFuture(event.replyAsync(exception.getMessage()))
                        .then())
                .toFuture();
    }
}
