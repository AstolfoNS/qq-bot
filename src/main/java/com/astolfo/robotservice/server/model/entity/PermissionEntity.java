package com.astolfo.robotservice.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("permission")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionEntity {

    @TableId
    private Long id;

    private String symbol;

    private String description;

    private String url;

    private String httpMethod;

    private String point;

    private Integer orderNum;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
