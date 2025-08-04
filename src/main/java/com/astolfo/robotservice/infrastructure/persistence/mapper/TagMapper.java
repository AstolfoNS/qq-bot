package com.astolfo.robotservice.infrastructure.persistence.mapper;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<TagEntity> {
}
