package com.astolfo.robotservice.bot.codeforces.api.impl.userhandle;

import com.astolfo.robotservice.bot.codeforces.model.dto.CodeForcesResponse;
import com.astolfo.robotservice.bot.codeforces.model.dto.UserInfo;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public interface CustomUserHandle {

    HandleType support();

    Mono<CodeForcesResponse<UserInfo>> handle(ClientResponse clientResponse);

}
