package com.astolfo.robotservice.domain.api;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesResponseDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesRatingHistoryDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesUserInfoDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesValidationResultDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeForcesClientApi {

    Mono<CodeForcesValidationResultDTO<CodeForcesUserInfoDTO>> getValidUserInfoIteratively(List<String> handles, boolean checkHistoricHandles);

    Mono<CodeForcesResponseDTO<CodeForcesRatingHistoryDTO>> getUserRatingHistory(String handle);

}