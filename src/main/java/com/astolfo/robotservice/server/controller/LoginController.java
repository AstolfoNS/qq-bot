package com.astolfo.robotservice.server.controller;

import com.astolfo.robotservice.server.common.responses.R;
import com.astolfo.robotservice.server.model.dto.LoginRequest;
import com.astolfo.robotservice.server.model.vo.LoginToken;
import com.astolfo.robotservice.server.service.LoginService;
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
    public R<LoginToken> login(@RequestBody LoginRequest loginRequest) {
        return loginRequest.validate().<R<LoginToken>>map(R::failure).orElseGet(() -> R.success(loginService.login(loginRequest)));
    }
}
