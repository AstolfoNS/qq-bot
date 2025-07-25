package com.astolfo.robotservice.server.mapper;

import com.astolfo.robotservice.server.model.entity.PermissionEntity;
import com.astolfo.robotservice.server.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    List<PermissionEntity> getPermissionListByUsername(@Param("username") String username);

}
