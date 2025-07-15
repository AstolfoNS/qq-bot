package com.astolfo.robotservice.codeforces.api.impl.handle;

import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class HandleDefaultUserResponse implements CustomUserHandle {

    @Override
    public HandleType support() {
        return HandleType.DEFAULT;
    }

    @Override
    public Mono<CodeForcesResponse<UserInfo>> handle(ClientResponse clientResponse) {
        return clientResponse.createException().flatMap(Mono::error);
    }
}
