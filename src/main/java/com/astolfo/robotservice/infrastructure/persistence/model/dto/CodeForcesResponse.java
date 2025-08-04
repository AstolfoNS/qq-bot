package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CodeForcesResponse<T> {

    private String status;

    private List<T> result;

    private String comment;


    public int getResultSize() {
        return Objects.isNull(result) ? 0 : result.size();
    }


    public static <T> CodeForcesResponse<T> emptyResponse() {
        CodeForcesResponse<T> emptyResponse = new CodeForcesResponse<>();

        emptyResponse.setStatus("OK");
        emptyResponse.setResult(new ArrayList<>());

        return emptyResponse;

    }

    public static <T> CodeForcesResponse<T> errorResponse() {
        return errorResponse("null");
    }

    public static <T> CodeForcesResponse<T> errorResponse(String comment) {
        CodeForcesResponse<T> response = new CodeForcesResponse<>();

        response.setStatus("FAILED");
        response.setResult(new ArrayList<>());
        response.setComment(comment);

        return response;
    }
}
