package com.astolfo.robotservice.server.service;

import com.astolfo.robotservice.server.model.dto.LoginRequest;
import com.astolfo.robotservice.server.model.vo.LoginToken;

public interface LoginService {

    LoginToken login(LoginRequest loginRequest);
}
