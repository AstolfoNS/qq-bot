package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequest;
import com.astolfo.robotservice.infrastructure.persistence.model.vo.LoginToken;

public interface LoginService {

    LoginToken login(LoginRequest loginRequest);
}
