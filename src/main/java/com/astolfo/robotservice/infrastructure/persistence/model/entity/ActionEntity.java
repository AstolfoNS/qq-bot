package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("action")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActionEntity {

    @TableId
    private Long id;

    private String actionName;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
