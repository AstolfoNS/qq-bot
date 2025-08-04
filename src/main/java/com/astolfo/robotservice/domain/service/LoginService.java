package com.astolfo.robotservice.domain.service;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequestDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.vo.LoginTokenVO;

public interface LoginService {

    LoginTokenVO login(LoginRequestDTO loginRequestDTO);
}
