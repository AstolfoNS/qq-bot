package com.astolfo.robotservice.infrastructure.persistence.model.vo;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginTokenVO {

    private UserEntity userEntity;

    private String token;

}
