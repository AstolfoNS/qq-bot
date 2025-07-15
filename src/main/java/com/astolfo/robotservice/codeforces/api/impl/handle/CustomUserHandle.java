package com.astolfo.robotservice.codeforces.api.impl.handle;

import com.astolfo.robotservice.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.codeforces.model.dto.UserInfo;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public interface CustomUserHandle {

    HandleType support();

    Mono<CodeForcesResponse<UserInfo>> handle(ClientResponse clientResponse);

}
