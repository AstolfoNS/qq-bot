package com.astolfo.robotservice.robot.domain.codeforces.service;

import love.forte.simbot.message.Messages;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeForcesService {

    Mono<List<Messages>> processUserInfo(List<String> handles, boolean checkHistoricHandles);

    Mono<Messages> processUserRatingHistory(String handle, int number);
}
