package com.astolfo.robotservice.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    String username;

    String password;


    public Optional<String> validate() {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Optional.of("用户名或密码为空");
        }

        return Optional.empty();
    }

}
