package com.astolfo.robotservice.robot.domain.lolicon.api.impl;

import com.astolfo.robotservice.robot.domain.lolicon.api.LoliconClientApi;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.LoliconResponse;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.PhotoInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
            String keyword
    ) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/setu/v2")
                        .queryParam("r18", r18)
                        .queryParam("num", number)
                        .queryParam("keyword", keyword)
                        .build()
                )
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }

                    return Mono.just(LoliconResponse.errorResponse());
                });
    }

}
