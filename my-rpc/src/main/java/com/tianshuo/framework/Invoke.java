package com.tianshuo.framework;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @ClassName : Invoke
 * @Description : 协议传输参数类  服务提供者通过这个类来调用接口
 * @Author : tianshuo
 * @Date: 2021-01-29 16:53
 */
@Data
public class Invoke implements Serializable {


    private String className;

    private String methodName;

    private Class[] paramType;

    private Object[] param;

    public Invoke() {

    }


    @Override
    public String toString() {
        return "Invoke{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramType=" + Arrays.toString(paramType) +
                ", param=" + Arrays.toString(param) +
                '}';
    }
}

