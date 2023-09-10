package com.example.guava.employees.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 员工记录表
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
@TableName("employees")
@Data
@EqualsAndHashCode(callSuper = false)
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    // 默认使用id为主键，并且当此值为null时，自动使用分布式唯一id
    // @TableId标识字段为主键,IdType.AUTO标识为数据库id自动增长
    @TableId(type = IdType.AUTO)
    private Integer id ;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private String position;

    /**
     * 入职时间
     */
    @TableField(value = "hire_time")
    private LocalDateTime hireTime;

    /**
     * 标识为逻辑删除
     * value 未删除
     * delVal 已删除标识
     */
    @TableLogic(value = "1",delval = "0")
    private Integer status;


}
