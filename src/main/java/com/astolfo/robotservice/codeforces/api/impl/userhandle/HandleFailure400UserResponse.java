package com.astolfo.robotservice.codeforces.api.impl.userhandle;

import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.UserInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class HandleFailure400UserResponse implements CustomUserHandle {

    @Override
    public HandleType support() {
        return HandleType.FAILURE400;
    }

    @Override
    public Mono<CodeForcesResponse<UserInfo>> handle(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(new ParameterizedTypeReference<CodeForcesResponse<UserInfo>>() {})
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new RuntimeException("Received 400 error with empty body from Codeforces API. Status: " + clientResponse.statusCode())));
    }
}
