package com.astolfo.robotservice.codeforces.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserValidationResult extends CodeForcesResponse<UserInfo> {
    private List<String> invalidHandles;

    public UserValidationResult(CodeForcesResponse<UserInfo> response, List<String> invalidHandles) {
        super(response.getStatus(),  response.getResult(), response.getComment());
        this.invalidHandles = invalidHandles;
    }

    public static UserValidationResult success(CodeForcesResponse<UserInfo> response, List<String> invalidHandles) {
        return new UserValidationResult(response, invalidHandles);
    }

    public static UserValidationResult error(List<String> invalidHandles) {
        return new UserValidationResult(CodeForcesResponse.errorResponse(), invalidHandles);
    }

    public static UserValidationResult empty() {
        return new UserValidationResult(CodeForcesResponse.emptyResponse(), Collections.emptyList());
    }
}
