package com.astolfo.robotservice.infrastructure.persistence.impl.api;

import com.astolfo.robotservice.domain.api.LoliconClientApi;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconResponse;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconPictureInfo;
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
    public Mono<LoliconResponse<LoliconPictureInfo>> getPicture(
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
