package com.jihu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页插件
 * 针对MybatisPlus的分页插件创建一个简单对象在存储需要的对象
 * @author jihu
 */
@Data
public class MPage<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> lists ;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 总记录数
     */
    private Long total;
}
