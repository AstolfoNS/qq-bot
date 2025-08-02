package com.astolfo.robotservice.robot.domain.lolicon.api;

import com.astolfo.robotservice.robot.domain.lolicon.model.dto.LoliconResponse;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.PhotoInfo;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponse<PhotoInfo>> getPhoto(
            String r18,
            int number,
            String tag
    );

}
