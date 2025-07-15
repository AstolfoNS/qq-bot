package com.astolfo.robotservice.codeforces.api.impl;

import com.astolfo.robotservice.codeforces.api.CodeForcesClientApi;
import com.astolfo.robotservice.codeforces.common.Constant;
import com.astolfo.robotservice.codeforces.common.ResponseStatus;
import com.astolfo.robotservice.codeforces.api.impl.handle.CustomUserHandle;
import com.astolfo.robotservice.codeforces.api.impl.handle.HandleType;
import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.UserInfo;
import com.astolfo.robotservice.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.codeforces.model.dto.UserValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CodeForcesClientApiImpl implements CodeForcesClientApi {

    private static final Pattern INVALID_HANDLE_PATTERN = Pattern.compile("handles:\\sUser\\swith\\shandle\\s(.*?)\\snot\\sfound");

    private final WebClient webClient;

    private Map<HandleType, CustomUserHandle> customUserHandleMap;

    @Autowired
    public void setCustomUserHandleMap(List<CustomUserHandle> customUserHandleList) {
        this.customUserHandleMap = customUserHandleList
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(CustomUserHandle::support, Function.identity()));
    };

    public CodeForcesClientApiImpl(WebClient.Builder webClientBuilder, @Value("${base-url.codeforces}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }


    @Override
    public Mono<UserValidationResult> getValidUserInfoIteratively(List<String> initialHandles, boolean checkHistoricHandles) {
        if (CollectionUtils.isEmpty(initialHandles)) {
            return Mono.just(UserValidationResult.empty());
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
                            return response.bodyToMono(new ParameterizedTypeReference<>() {});
                        } else {
                            return Mono.just(CodeForcesResponse.errorResponse());
                        }
                    } );
        }
    }

    private Mono<UserValidationResult> validateHandlesRecursively(
            List<String> handles,
            boolean checkHistoricHandles,
            List<String> invalidHandles
    ) {
        if (handles.isEmpty()) {
            return Mono.just(UserValidationResult.error(invalidHandles));
        } else {
            return fetchUserInfo(handles, checkHistoricHandles).flatMap(response -> {
                        if (Constant.OK.equals(response.getStatus())) {
                            return Mono.just(UserValidationResult.success(response, invalidHandles));
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
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user.info")
                            .queryParam("handles", String.join(";", handles))
                            .queryParam("checkHistoricHandles", checkHistoricHandles)
                            .build())
                    .exchangeToMono(response -> customUserHandleMap.get(HandleType.typeOf(response)).handle(response));
        }

    }

    private Mono<UserValidationResult> handleInvalidResponse(
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
                .orElse(Mono.just(UserValidationResult.error(invalidHandles)));
    }

    private Optional<String> extractInvalidHandle(String comment) {
        if (!StringUtils.hasText(comment)) {
            return Optional.empty();
        } else {
            Matcher matcher = INVALID_HANDLE_PATTERN.matcher(comment);

            return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
        }

    }
}