package com.astolfo.robotservice.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {

    @TableId
    private Long id;

    private String qqEmail;

    private String username;

    private String nickname;

    private String password;

    private String avatar;

    private Integer gender;

    private String introduction;

    private LocalDateTime lastLoginTime;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
