package com.astolfo.robotservice.infrastructure.persistence.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("qq_id_action")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QqIdActionEntity {

    @TableId
    private Long id;

    private String qqId;

    private String action;

    private LocalDateTime createTime;

    private Boolean isDeleted;

}
