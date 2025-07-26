package com.astolfo.robotservice.server.model.vo;

import com.astolfo.robotservice.server.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginToken {

    private UserEntity userEntity;

    private String token;

}
