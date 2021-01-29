package com.tianshuo.framework;

import java.io.Serializable;

/**
 * @ClassName : Invoke
 * @Description : 协议传输参数类  服务提供者通过这个类来调用接口
 * @Author : tianshuo
 * @Date: 2021-01-29 16:53
 */
public class Invoke implements Serializable {


    private String className;

    private String methodName;

    private Object[] paramType;

    private Object[] param;

    public Invoke() {

    }

    public Invoke(String className, String methodName, Class[] paramType, Object[] param) {
        this.className = className;
        this.methodName = methodName;
        this.paramType = paramType;
        this.param = param;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParamType() {
        return paramType;
    }

    public void setParamType(Object[] paramType) {
        this.paramType = paramType;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }
}

