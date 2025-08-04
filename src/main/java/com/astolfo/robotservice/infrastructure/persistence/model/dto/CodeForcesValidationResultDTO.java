package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CodeForcesValidationResultDTO<T> extends CodeForcesResponseDTO<T> {

    private List<String> invalidHandles;

    public CodeForcesValidationResultDTO(CodeForcesResponseDTO<T> response, List<String> invalidHandles) {
        super(response.getStatus(),  response.getResult(), response.getComment());

        this.invalidHandles = invalidHandles;
    }

    public static <T> CodeForcesValidationResultDTO<T> success(CodeForcesResponseDTO<T> response, List<String> invalidHandles) {
        return new CodeForcesValidationResultDTO<>(response, invalidHandles);
    }

    public static <T> CodeForcesValidationResultDTO<T> error(List<String> invalidHandles) {
        return new CodeForcesValidationResultDTO<>(CodeForcesResponseDTO.errorResponse(), invalidHandles);
    }

    public static <T> CodeForcesValidationResultDTO<T> empty() {
        return new CodeForcesValidationResultDTO<>(CodeForcesResponseDTO.emptyResponse(), Collections.emptyList());
    }
}
