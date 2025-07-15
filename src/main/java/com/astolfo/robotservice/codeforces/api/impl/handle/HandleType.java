package com.astolfo.robotservice.codeforces.api.impl.handle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum HandleType {

    SUCCESS200((ClientResponse clientResponse) -> clientResponse.statusCode().value() == 200),
    FAILURE400((ClientResponse clientResponse) -> clientResponse.statusCode().value() == 400),
    DEFAULT((ClientResponse clientResponse) -> true);


    public final ClientResponseStatusCode statusCodeFunction;


    public static HandleType typeOf(ClientResponse clientResponse) {
        return Arrays
                .stream(HandleType.values())
                .filter(handleType -> handleType.statusCodeFunction.canHandle(clientResponse))
                .findFirst()
                .orElse(DEFAULT);
    }

    public interface ClientResponseStatusCode {
        boolean canHandle(ClientResponse clientResponse);
    }
}

