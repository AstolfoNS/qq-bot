package com.astolfo.robotservice.robot.domain.codeforces.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationResult<T> extends CodeForcesResponse<T> {

    private List<String> invalidHandles;

    public ValidationResult(CodeForcesResponse<T> response, List<String> invalidHandles) {
        super(response.getStatus(),  response.getResult(), response.getComment());

        this.invalidHandles = invalidHandles;
    }

    public static <T> ValidationResult<T> success(CodeForcesResponse<T> response, List<String> invalidHandles) {
        return new ValidationResult<>(response, invalidHandles);
    }

    public static <T> ValidationResult<T> error(List<String> invalidHandles) {
        return new ValidationResult<>(CodeForcesResponse.errorResponse(), invalidHandles);
    }

    public static <T> ValidationResult<T> empty() {
        return new ValidationResult<>(CodeForcesResponse.emptyResponse(), Collections.emptyList());
    }
}
