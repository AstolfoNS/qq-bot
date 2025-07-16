package com.astolfo.robotservice.codeforces.api.impl.userhandle;

import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.UserInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class HandleSuccess200UserResponse implements CustomUserHandle {

    @Override
    public HandleType support() {
        return HandleType.SUCCESS200;
    }

    @Override
    public Mono<CodeForcesResponse<UserInfo>> handle(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
