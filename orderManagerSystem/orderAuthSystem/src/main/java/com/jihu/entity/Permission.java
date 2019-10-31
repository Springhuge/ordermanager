package com.jihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 许可
 * @author jihu
 */
@Data
@TableName("sys_permission")
public class Permission implements Serializable {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 许可url
     */
    private String permission;

    /**
     * 许可描述
     */
    private String description;

    /**
     * 乐观锁插件
     */
    private Integer version;

    /**
     * 0，未删除，1，已删除
     * 逻辑删除标识符
     */
    private Integer deleted;
}
