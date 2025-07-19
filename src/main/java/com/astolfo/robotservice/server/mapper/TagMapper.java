package com.astolfo.robotservice.server.mapper;

import com.astolfo.robotservice.server.model.entity.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<TagEntity> {
}
