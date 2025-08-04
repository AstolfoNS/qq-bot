package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("photo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureEntity {

    @TableId
    private Long id;

    private Long pid;

    private Long uid;

    private Integer p;

    private String title;

    private String author;

    private Boolean r18;

    private Integer width;

    private Integer height;

    private String ext;

    private Integer aiType;

    private Long uploadDate;

    private String originalUrl;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;

}
