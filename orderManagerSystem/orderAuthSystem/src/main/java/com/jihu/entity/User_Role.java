package com.jihu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("sys_user_role")
public class User_Role {

    @Id
    private String id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * MybatisPlus 乐观锁插件
     */
    private String version;

    /**
     * 逻辑删除标识符：
     * 0，未删除，1，已删除
     */
    private String deleted;

    private User user;

    private Role role;

}
