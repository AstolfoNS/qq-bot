package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("photo_tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureTagEntity {

    @TableId
    private Long id;

    private Long pictureId;

    private Long tagId;

    private LocalDateTime createTime;

    private Boolean isDeleted;

}
