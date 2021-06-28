package com.example.guava.employees.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工记录表
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
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
    private LocalDateTime hireTime;


}
