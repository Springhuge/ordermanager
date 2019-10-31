package com.jihu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jihu.entity.Role;
import com.jihu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
