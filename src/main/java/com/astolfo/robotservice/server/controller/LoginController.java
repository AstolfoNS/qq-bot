package com.astolfo.robotservice.server.controller;

import com.astolfo.robotservice.server.common.responses.R;
import com.astolfo.robotservice.server.model.dto.LoginRequest;
import com.astolfo.robotservice.server.model.vo.LoginToken;
import com.astolfo.robotservice.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Resource
    UserService userService;


    @PostMapping
    public R<LoginToken> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return R.failure("无效的密码或用户名");
        }




        return null;
    }
}
