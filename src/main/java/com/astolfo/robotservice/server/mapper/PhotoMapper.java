package com.astolfo.robotservice.server.mapper;

import com.astolfo.robotservice.server.model.entity.PhotoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoMapper extends BaseMapper<PhotoEntity> {
}
