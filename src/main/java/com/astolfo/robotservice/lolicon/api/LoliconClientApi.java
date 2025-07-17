package com.astolfo.robotservice.lolicon.api;

import com.astolfo.robotservice.lolicon.model.dto.LoliconResponseV1;
import com.astolfo.robotservice.lolicon.model.dto.LoliconResponseV2;
import com.astolfo.robotservice.lolicon.model.dto.PhotoInfoV1;
import com.astolfo.robotservice.lolicon.model.dto.PhotoInfoV2;
import reactor.core.publisher.Mono;

public interface LoliconClientApi {

    Mono<LoliconResponseV1<PhotoInfoV1>> getPhotoV1();

    Mono<LoliconResponseV2<PhotoInfoV2>> getPhotoV2();
}
