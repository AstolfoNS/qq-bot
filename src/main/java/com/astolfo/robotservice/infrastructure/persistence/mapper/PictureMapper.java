package com.astolfo.robotservice.infrastructure.persistence.mapper;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.PictureEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureMapper extends BaseMapper<PictureEntity> {
}
