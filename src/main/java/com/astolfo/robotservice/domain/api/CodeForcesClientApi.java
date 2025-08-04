package com.astolfo.robotservice.domain.api;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesRatingHistory;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesUserInfo;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesValidationResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeForcesClientApi {

    Mono<CodeForcesValidationResult<CodeForcesUserInfo>> getValidUserInfoIteratively(List<String> handles, boolean checkHistoricHandles);

    Mono<CodeForcesResponse<CodeForcesRatingHistory>> getUserRatingHistory(String handle);

}