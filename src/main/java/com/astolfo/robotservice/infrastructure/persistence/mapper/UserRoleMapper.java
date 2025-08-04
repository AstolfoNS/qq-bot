package com.astolfo.robotservice.infrastructure.persistence.mapper;

import com.astolfo.robotservice.infrastructure.persistence.model.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
}
