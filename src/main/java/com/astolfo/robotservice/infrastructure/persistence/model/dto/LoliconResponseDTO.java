package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoliconResponseDTO<T> {

    private String error;

    private List<T> data;


    public static <T> LoliconResponseDTO<T> emptyResponse() {
        LoliconResponseDTO<T> emptyResponse = new LoliconResponseDTO<>();

        emptyResponse.setData(new ArrayList<>());

        return emptyResponse;
    }

    public static <T> LoliconResponseDTO<T> errorResponse() {
        return errorResponse("null");
    }

    public static <T> LoliconResponseDTO<T> errorResponse(String error) {
        LoliconResponseDTO<T> response = new LoliconResponseDTO<>();

        response.setError(error);
        response.setData(new ArrayList<>());

        return response;
    }
}
