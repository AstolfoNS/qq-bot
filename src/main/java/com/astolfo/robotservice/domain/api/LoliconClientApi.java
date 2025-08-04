package com.astolfo.robotservice.domain.api;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconResponseDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconPictureInfoDTO;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponseDTO<LoliconPictureInfoDTO>> getPicture(
            String r18,
            int number,
            String keyword
    );

}
