package com.astolfo.robotservice.codeforces.service.impl;

import com.astolfo.robotservice.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.codeforces.service.template.RatingHistoryTemplate;
import com.astolfo.robotservice.common.service.template.StringTemplate;
import com.astolfo.robotservice.codeforces.service.template.UserInfoTemplate;
import com.astolfo.robotservice.codeforces.service.CodeForcesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class CodeForcesServiceImpl implements CodeForcesService {

    @Resource
    private CodeForcesClientApi codeForcesClientApi;

    @Resource
    private UserInfoTemplate userInfoTemplate;

    @Resource
    private RatingHistoryTemplate ratingHistoryTemplate;


    private static final int MAX_REQUEST_USER_LIST_SIZE = 10;

    private static final int MAX_RATING_HISTORY_SIZE = 50;


    @Override
    public Mono<List<Messages>> processUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if(handles.size() > MAX_REQUEST_USER_LIST_SIZE) {
            return Mono.just(List.of(StringTemplate.toMessages(String.format("最多只能查询%d条用户信息", MAX_REQUEST_USER_LIST_SIZE))));
        } else {
            return codeForcesClientApi
                    .getValidUserInfoIteratively(handles, checkHistoricHandles)
                    .map(userValidationResult -> userInfoTemplate.toMessages(userValidationResult.getSuccessResponse().getResult()));
        }
    }

    @Override
    public Mono<Messages> processUserRatingHistory(String handle, int number) {
        if (number > MAX_REQUEST_USER_LIST_SIZE) {
            return Mono.just(StringTemplate.toMessages(String.format( "最多只支持查询最近的%d条数据", MAX_RATING_HISTORY_SIZE)));
        } else {
             return codeForcesClientApi
                    .getUserRatingHistory(handle)
                    .map(response -> ratingHistoryTemplate.toMessages(response.getResult(), handle, Math.max(0, response.getResultSize() - number)));
        }
    }

}
