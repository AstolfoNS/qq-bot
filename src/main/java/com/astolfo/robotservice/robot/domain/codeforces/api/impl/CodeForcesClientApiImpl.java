package com.astolfo.robotservice.robot.domain.codeforces.api.impl;

import com.astolfo.robotservice.robot.domain.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.robot.domain.basic.constant.CodeForcesConstant;
import com.astolfo.robotservice.robot.domain.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.robot.domain.codeforces.model.dto.UserInfo;
import com.astolfo.robotservice.robot.domain.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.robot.domain.codeforces.model.dto.ValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;

@Component
public class CodeForcesClientApiImpl implements CodeForcesClientApi {

    private final WebClient webClient;


    public CodeForcesClientApiImpl(WebClient.Builder webClientBuilder, @Value("${base-url.codeforces}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }


    @Override
    public Mono<ValidationResult<UserInfo>> getValidUserInfoIteratively(List<String> initialHandles, boolean checkHistoricHandles) {
        if (CollectionUtils.isEmpty(initialHandles)) {
            return Mono.just(ValidationResult.empty());
        }

        return validateHandlesRecursively(new ArrayList<>(initialHandles), checkHistoricHandles, new ArrayList<>());
    }

    @Override
    public Mono<CodeForcesResponse<RatingHistory>> getUserRatingHistory(String handle) {
        if (!StringUtils.hasText(handle)) {
            return Mono.just(CodeForcesResponse.errorResponse());
        }

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user.rating")
                        .queryParam("handle", handle)
                        .build()
                )
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }

                    return Mono.just(CodeForcesResponse.errorResponse());
                });
    }

    private Mono<ValidationResult<UserInfo>> validateHandlesRecursively(
            List<String> handles,
            boolean checkHistoricHandles,
            List<String> invalidHandles
    ) {
        if (handles.isEmpty()) {
            return Mono.just(ValidationResult.error(invalidHandles));
        }

        return fetchUserInfo(handles, checkHistoricHandles).flatMap(response -> {
            if (CodeForcesConstant.OK.equals(response.getStatus())) {
                return Mono.just(ValidationResult.success(response, invalidHandles));
            }

            return handleInvalidResponse(response, handles, checkHistoricHandles, invalidHandles);
        });

    }

    private Mono<CodeForcesResponse<UserInfo>> fetchUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if (CollectionUtils.isEmpty(handles)) {
            return Mono.just(CodeForcesResponse.emptyResponse());
        }

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user.info")
                        .queryParam("handles", String.join(";", handles))
                        .queryParam("checkHistoricHandles", checkHistoricHandles)
                        .build()
                )
                .exchangeToMono(response -> {
                    if (response.statusCode().value() >= 200 && response.statusCode().value() <= 300) {
                        return response.bodyToMono(new ParameterizedTypeReference<>() {});
                    }
                    if (response.statusCode().value() >= 400 && response.statusCode().value() <= 500) {
                        return response
                                .bodyToMono(new ParameterizedTypeReference<CodeForcesResponse<UserInfo>>() {})
                                .flatMap(Mono::just)
                                .switchIfEmpty(Mono.error(new RuntimeException("Received 400 error with empty body from Codeforces API. Status: " + response.statusCode())));
                    }

                    return response.createException().flatMap(Mono::error);
                });
    }

    private Mono<ValidationResult<UserInfo>> handleInvalidResponse(
            CodeForcesResponse<UserInfo> response,
            List<String> handles,
            boolean checkHistoricHandles,
            List<String> invalidHandles
    ) {
        return extractInvalidHandle(response.getComment())
                .filter(handles::contains)
                .map(invalidHandle -> {
                    invalidHandles.add(invalidHandle);
                    handles.remove(invalidHandle);

                    return validateHandlesRecursively(handles, checkHistoricHandles, invalidHandles);
                })
                .orElse(Mono.just(ValidationResult.error(invalidHandles)));
    }

    private Optional<String> extractInvalidHandle(String comment) {
        if (!StringUtils.hasText(comment)) {
            return Optional.empty();
        }

        Matcher matcher = CodeForcesConstant.INVALID_HANDLE_PATTERN.matcher(comment);

        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }
}