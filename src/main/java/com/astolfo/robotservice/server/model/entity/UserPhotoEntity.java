package com.astolfo.robotservice.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("user_permission")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPhotoEntity {

    @TableId
    private Long id;

    private Long userId;

    private Long photoId;

    private LocalDateTime createTime;

    private Boolean isDeleted;

}
