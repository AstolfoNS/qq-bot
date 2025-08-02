package com.astolfo.robotservice.robot.domain.codeforces.service.impl;

import com.astolfo.robotservice.robot.domain.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.robot.domain.basic.constant.CodeForcesConstant;
import com.astolfo.robotservice.robot.domain.codeforces.model.template.RatingHistoryTemplate;
import com.astolfo.robotservice.robot.domain.basic.template.StringTemplate;
import com.astolfo.robotservice.robot.domain.codeforces.model.template.UserInfoTemplate;
import com.astolfo.robotservice.robot.domain.codeforces.service.CodeForcesService;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CodeForcesServiceImpl implements CodeForcesService {

    @Resource
    private CodeForcesClientApi codeForcesClientApi;

    @Resource
    private UserInfoTemplate userInfoTemplate;

    @Resource
    private RatingHistoryTemplate ratingHistoryTemplate;


    @Override
    public Mono<List<Messages>> processUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if(handles.size() > CodeForcesConstant.MAX_REQUEST_USER_LIST_SIZE) {
            return Mono.just(List.of(StringTemplate.toMessages(String.format("最多只能查询%d条用户信息", CodeForcesConstant.MAX_REQUEST_USER_LIST_SIZE))));
        }

        return codeForcesClientApi
                .getValidUserInfoIteratively(handles, checkHistoricHandles)
                .handle((response, sink) -> {
                    if (CodeForcesConstant.OK.equals(response.getStatus())) {
                        sink.next(userInfoTemplate.toMessages(response.getResult()));
                    } else {
                        sink.error(new RuntimeException(response.getComment()));
                    }
                });
    }

    @Override
    public Mono<Messages> processUserRatingHistory(String handle, int number) {
        if (number > CodeForcesConstant.MAX_RATING_HISTORY_SIZE) {
            return Mono.just(StringTemplate.toMessages(String.format("最多只支持查询最近的%d条数据", CodeForcesConstant.MAX_RATING_HISTORY_SIZE)));
        }

        return codeForcesClientApi
                .getUserRatingHistory(handle)
                .handle((response, sink) -> {
                    if (CodeForcesConstant.OK.equals(response.getStatus())) {
                        sink.next(ratingHistoryTemplate.toMessages(response.getResult(), handle, Math.max(0, response.getResultSize() - number)));
                    } else {
                        sink.error(new RuntimeException(response.getComment()));
                    }
                });
    }

}
