package com.astolfo.robotservice.infrastructure.persistence.impl.service;

import com.astolfo.robotservice.domain.api.CodeForcesClientApi;
import com.astolfo.robotservice.infrastructure.common.constants.CodeForcesConstant;
import com.astolfo.robotservice.infrastructure.persistence.template.CodeForcesRatingHistoryTemplate;
import com.astolfo.robotservice.infrastructure.persistence.template.StringTemplate;
import com.astolfo.robotservice.infrastructure.persistence.template.CodeForcesUserInfoTemplate;
import com.astolfo.robotservice.domain.service.CodeForcesService;
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
    private CodeForcesUserInfoTemplate codeForcesUserInfoTemplate;

    @Resource
    private CodeForcesRatingHistoryTemplate codeForcesRatingHistoryTemplate;


    @Override
    public Mono<List<Messages>> processUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if(handles.size() > CodeForcesConstant.MAX_REQUEST_USER_LIST_SIZE) {
            return Mono.just(List.of(StringTemplate.toMessages(String.format("最多只能查询%d条用户信息", CodeForcesConstant.MAX_REQUEST_USER_LIST_SIZE))));
        }

        return codeForcesClientApi
                .getValidUserInfoIteratively(handles, checkHistoricHandles)
                .handle((response, sink) -> {
                    if (CodeForcesConstant.OK.equals(response.getStatus())) {
                        sink.next(codeForcesUserInfoTemplate.toMessages(response.getResult()));
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
                        sink.next(codeForcesRatingHistoryTemplate.toMessages(response.getResult(), handle, Math.max(0, response.getResultSize() - number)));
                    } else {
                        sink.error(new RuntimeException(response.getComment()));
                    }
                });
    }

}
