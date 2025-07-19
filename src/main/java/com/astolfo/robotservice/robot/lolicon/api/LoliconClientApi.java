package com.astolfo.robotservice.robot.lolicon.api;

import com.astolfo.robotservice.robot.lolicon.model.dto.LoliconResponse;
import com.astolfo.robotservice.robot.lolicon.model.dto.PhotoInfo;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponse<PhotoInfo>> getPhoto(
            String r18,
            int number,
            String tag
    );

}
