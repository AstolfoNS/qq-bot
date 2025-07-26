package com.astolfo.robotservice.robot.lolicon.api.impl;

import com.astolfo.robotservice.robot.basic.constant.LoliconConstant;
import com.astolfo.robotservice.robot.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.robot.lolicon.model.dto.LoliconResponse;
import com.astolfo.robotservice.robot.lolicon.model.dto.PhotoInfo;
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
    public Mono<LoliconResponse<PhotoInfo>> getPhoto(
            String r18,
            int number,
            String tag
    ) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/setu/v2")
                        .queryParam("r18", r18)
                        .queryParam("num", number)
                        .queryParam("keyword", tag)
                        .build()
                )
                .exchangeToMono(response -> {
                    log.info("getPhotoV2:response = {}", response);

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }

                    return Mono.just(LoliconResponse.errorResponse());
                });
    }

}
