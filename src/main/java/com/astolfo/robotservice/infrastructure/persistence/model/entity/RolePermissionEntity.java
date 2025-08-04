package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("role_permission")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RolePermissionEntity {

    @TableId
    private Long id;

    private Long userId;

    private Long roleId;

    private LocalDateTime createTime;

    private Boolean isDeleted;

}
