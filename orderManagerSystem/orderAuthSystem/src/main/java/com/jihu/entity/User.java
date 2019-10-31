package com.jihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 * @author jihu
 */
@Data
@TableName("sys_user")
public class User implements Serializable {

    /**
     * id
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 乐观锁
     * mybatisplus插件
     */
    private String version;

    /**
     * 逻辑删除标识（0，未删除,1，已删除）
     */
    private Integer deleted;

    /**
     * 性别
     *  m 为 男
     *  w 为 女
     */
    private String sex;

    /**
     * 一对多关系
     * 一个用户可以拥有多个角色
     */
    @TableField(exist = false)
    private  List<Role> roleList;

}
