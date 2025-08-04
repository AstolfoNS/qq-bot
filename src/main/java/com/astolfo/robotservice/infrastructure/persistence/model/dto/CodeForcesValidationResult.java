package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CodeForcesValidationResult<T> extends CodeForcesResponse<T> {

    private List<String> invalidHandles;

    public CodeForcesValidationResult(CodeForcesResponse<T> response, List<String> invalidHandles) {
        super(response.getStatus(),  response.getResult(), response.getComment());

        this.invalidHandles = invalidHandles;
    }

    public static <T> CodeForcesValidationResult<T> success(CodeForcesResponse<T> response, List<String> invalidHandles) {
        return new CodeForcesValidationResult<>(response, invalidHandles);
    }

    public static <T> CodeForcesValidationResult<T> error(List<String> invalidHandles) {
        return new CodeForcesValidationResult<>(CodeForcesResponse.errorResponse(), invalidHandles);
    }

    public static <T> CodeForcesValidationResult<T> empty() {
        return new CodeForcesValidationResult<>(CodeForcesResponse.emptyResponse(), Collections.emptyList());
    }
}
