package com.astolfo.robotservice.codeforces.api;

import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.codeforces.model.dto.UserValidationResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeForcesClientApi {

    Mono<UserValidationResult> getValidUserInfoIteratively(List<String> handles, boolean checkHistoricHandles);

    Mono<CodeForcesResponse<RatingHistory>> getUserRatingHistory(String handle);

}