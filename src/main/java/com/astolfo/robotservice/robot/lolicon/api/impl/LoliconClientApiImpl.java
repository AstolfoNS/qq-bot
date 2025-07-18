package com.astolfo.robotservice.robot.lolicon.api.impl;

import com.astolfo.robotservice.robot.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.robot.lolicon.model.dto.LoliconResponseV1;
import com.astolfo.robotservice.robot.lolicon.model.dto.LoliconResponseV2;
import com.astolfo.robotservice.robot.lolicon.model.dto.PhotoInfoV1;
import com.astolfo.robotservice.robot.lolicon.model.dto.PhotoInfoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoliconClientApiImpl implements LoliconClientApi {

    private final WebClient webClient;


    public LoliconClientApiImpl(WebClient.Builder webClientBuilder, @Value("${base-url.lolicon}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }


    @Override
    public Mono<LoliconResponseV1<PhotoInfoV1>> getPhotoV1() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/setu/v1")
                        .build()
                )
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        log.info("getPhotoV1:response = {}", response);

                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }

                    return Mono.just(LoliconResponseV1.errorResponse());
                });
    }

    @Override
    public Mono<LoliconResponseV2<PhotoInfoV2>> getPhotoV2() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/setu/v2")
                        .build()
                )
                .exchangeToMono(response -> {
                    log.info("getPhotoV2:response = {}", response);

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }

                    return Mono.just(LoliconResponseV2.errorResponse());
                });
    }

}
