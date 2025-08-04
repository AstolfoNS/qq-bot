package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleEntity {

    @TableId
    private Long id;

    private String roleName;

    private String description;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
