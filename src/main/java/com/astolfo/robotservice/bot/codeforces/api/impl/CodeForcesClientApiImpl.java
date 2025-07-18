package com.astolfo.robotservice.bot.codeforces.api.impl;

import com.astolfo.robotservice.bot.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.bot.codeforces.common.Constant;
import com.astolfo.robotservice.bot.codeforces.api.impl.userhandle.CustomUserHandle;
import com.astolfo.robotservice.bot.codeforces.api.impl.userhandle.HandleType;
import com.astolfo.robotservice.bot.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.bot.codeforces.model.dto.UserInfo;
import com.astolfo.robotservice.bot.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.bot.codeforces.model.dto.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CodeForcesClientApiImpl implements CodeForcesClientApi {


    private final WebClient webClient;

    private final Map<HandleType, CustomUserHandle> customUserHandleMap;


    public CodeForcesClientApiImpl(
            WebClient.Builder webClientBuilder,
            @Value("${base-url.codeforces}") String baseUrl,
            List<CustomUserHandle> customUserHandleList
        ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();

        this.customUserHandleMap = customUserHandleList
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(CustomUserHandle::support, Function.identity()));
    }


    @Override
    public Mono<ValidationResult<UserInfo>> getValidUserInfoIteratively(List<String> initialHandles, boolean checkHistoricHandles) {
        if (CollectionUtils.isEmpty(initialHandles)) {
            return Mono.just(ValidationResult.empty());
        } else {
            return validateHandlesRecursively(new ArrayList<>(initialHandles), checkHistoricHandles, new ArrayList<>());
        }
    }

    @Override
    public Mono<CodeForcesResponse<RatingHistory>> getUserRatingHistory(String handle) {
        if (!StringUtils.hasText(handle)) {
            return Mono.just(CodeForcesResponse.errorResponse());
        } else {
            return webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user.rating")
                            .queryParam("handle", handle)
                            .build()
                    )
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            log.info("getUserRatingHistory:response = {}", response);

                            return response.bodyToMono(new ParameterizedTypeReference<>() {});
                        } else {
                            return Mono.just(CodeForcesResponse.errorResponse());
                        }
                    });
        }
    }

    private Mono<ValidationResult<UserInfo>> validateHandlesRecursively(
            List<String> handles,
            boolean checkHistoricHandles,
            List<String> invalidHandles
    ) {
        if (handles.isEmpty()) {
            return Mono.just(ValidationResult.error(invalidHandles));
        } else {
            return fetchUserInfo(handles, checkHistoricHandles).flatMap(response -> {
                if (Constant.OK.equals(response.getStatus())) {
                    return Mono.just(ValidationResult.success(response, invalidHandles));
                } else {
                    return handleInvalidResponse(response, handles, checkHistoricHandles, invalidHandles);
                }
            });
        }
    }

    private Mono<CodeForcesResponse<UserInfo>> fetchUserInfo(List<String> handles, boolean checkHistoricHandles) {
        if (CollectionUtils.isEmpty(handles)) {
            return Mono.just(CodeForcesResponse.emptyResponse());
        } else {
            return webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user.info")
                            .queryParam("handles", String.join(";", handles))
                            .queryParam("checkHistoricHandles", checkHistoricHandles)
                            .build()
                    )
                    .exchangeToMono(response -> {
                        log.info("getUserInfo:response = {}", response);
                        
                        return customUserHandleMap.get(HandleType.typeOf(response)).handle(response);
                    });
        }
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
        } else {
            Matcher matcher = Constant.INVALID_HANDLE_PATTERN.matcher(comment);

            return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
        }
    }
}