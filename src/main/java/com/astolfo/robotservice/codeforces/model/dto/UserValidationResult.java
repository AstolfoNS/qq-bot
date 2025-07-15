package com.astolfo.robotservice.codeforces.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserValidationResult {

    private CodeForcesResponse<UserInfo> successResponse;

    private List<String> invalidHandles;

}
