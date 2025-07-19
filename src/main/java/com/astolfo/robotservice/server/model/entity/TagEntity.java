package com.astolfo.robotservice.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TagEntity {

    @TableId
    private Long id;

    private String tagName;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
