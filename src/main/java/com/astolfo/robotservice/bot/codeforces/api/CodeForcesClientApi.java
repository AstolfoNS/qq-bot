package com.astolfo.robotservice.bot.codeforces.api;

import com.astolfo.robotservice.bot.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.bot.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.bot.codeforces.model.dto.UserInfo;
import com.astolfo.robotservice.bot.codeforces.model.dto.ValidationResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeForcesClientApi {

    Mono<ValidationResult<UserInfo>> getValidUserInfoIteratively(List<String> handles, boolean checkHistoricHandles);

    Mono<CodeForcesResponse<RatingHistory>> getUserRatingHistory(String handle);

}