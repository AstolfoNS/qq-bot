package com.astolfo.robotservice.bot.codeforces.api.impl.userhandle;

import com.astolfo.robotservice.bot.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.bot.codeforces.model.dto.UserInfo;
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
