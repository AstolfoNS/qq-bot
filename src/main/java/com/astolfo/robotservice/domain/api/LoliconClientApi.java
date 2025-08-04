package com.astolfo.robotservice.domain.api;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconResponse;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconPictureInfo;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponse<LoliconPictureInfo>> getPicture(
            String r18,
            int number,
            String keyword
    );

}
