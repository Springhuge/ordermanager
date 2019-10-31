package com.jihu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jihu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
