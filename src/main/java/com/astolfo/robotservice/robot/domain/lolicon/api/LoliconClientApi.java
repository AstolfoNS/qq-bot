package com.astolfo.robotservice.robot.domain.lolicon.api;

import com.astolfo.robotservice.robot.domain.lolicon.model.dto.LoliconResponse;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.PictureInfo;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponse<PictureInfo>> getPicture(
            String r18,
            int number,
            String keyword
    );

}
