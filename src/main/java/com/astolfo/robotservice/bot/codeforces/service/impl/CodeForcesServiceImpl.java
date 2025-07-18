package com.astolfo.robotservice.bot.codeforces.service.impl;

import com.astolfo.robotservice.bot.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.bot.codeforces.common.Constant;
import com.astolfo.robotservice.infrastructure.utils.CommonConstant;
import com.astolfo.robotservice.bot.codeforces.model.template.RatingHistoryTemplate;
import com.astolfo.robotservice.bot.basic.template.StringTemplate;
import com.astolfo.robotservice.bot.codeforces.model.template.UserInfoTemplate;
import com.astolfo.robotservice.bot.codeforces.service.CodeForcesService;
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


    @Override
    public Mono<List<Messages>> processUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if(handles.size() > Constant.MAX_REQUEST_USER_LIST_SIZE) {
            return Mono.just(List.of(StringTemplate.toMessages(String.format("最多只能查询%d条用户信息", Constant.MAX_REQUEST_USER_LIST_SIZE))));
        }

        return codeForcesClientApi
                .getValidUserInfoIteratively(handles, checkHistoricHandles)
                .handle((response, sink) -> {
                    if (Constant.OK.equals(response.getStatus())) {
                        log.info("processUserInfo:response = {}", response);

                        sink.next(userInfoTemplate.toMessages(response.getResult()));
                    }

                    sink.error(new RuntimeException(CommonConstant.FAILED_MESSAGE + response.getComment()));
                });
    }

    @Override
    public Mono<Messages> processUserRatingHistory(String handle, int number) {
        if (number > Constant.MAX_RATING_HISTORY_SIZE) {
            return Mono.just(StringTemplate.toMessages(String.format( "最多只支持查询最近的%d条数据", Constant.MAX_RATING_HISTORY_SIZE)));
        }

        return codeForcesClientApi
                .getUserRatingHistory(handle)
                .handle((response, sink) -> {
                    if (Constant.OK.equals(response.getStatus())) {
                        log.info("processUserRatingHistory:response = {}", response);

                        sink.next(ratingHistoryTemplate.toMessages(response.getResult(), handle, Math.max(0, response.getResultSize() - number)));
                    }

                    sink.error(new RuntimeException(CommonConstant.FAILED_MESSAGE + response.getComment()));
                });
    }

}
