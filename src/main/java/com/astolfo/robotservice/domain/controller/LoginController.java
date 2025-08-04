package com.astolfo.robotservice.domain.controller;

import com.astolfo.robotservice.infrastructure.common.responses.R;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoginRequestDTO;
import com.astolfo.robotservice.infrastructure.persistence.model.vo.LoginTokenVO;
import com.astolfo.robotservice.domain.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;


    @PostMapping
    public R<LoginTokenVO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return loginRequestDTO.validate().<R<LoginTokenVO>>map(R::failure).orElseGet(() -> R.success(loginService.login(loginRequestDTO)));
    }
}
