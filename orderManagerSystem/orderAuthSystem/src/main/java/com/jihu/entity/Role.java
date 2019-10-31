package com.jihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 * @author jihu
 */
@Data
@TableName("sys_role")
public class Role implements Serializable {

    /**
     * id
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 角色名
     */
    private String role;

    /**
     * 描述
     */
    private String description;

    /**
     * 乐观锁
     * mybatisplus插件
     */
    private String version;

    /**
     * 逻辑删除标识（0，未删除,1，已删除）
     */
    private Integer deleted;
}
