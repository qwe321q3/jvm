package com.tianshuo.framework;


import java.io.Serializable;
import java.util.Arrays;

/**
 * @ClassName : Invoke
 * @Description : 协议传输参数类  服务提供者通过这个类来调用接口
 * @Author : tianshuo
 * @Date: 2021-01-29 16:53
 */
public class Invoke implements Serializable {

    private String interfaceName;

    private String methodName;

    private Class[] paramType;

    private Object[] param;

    public Invoke() {

    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamType() {
        return paramType;
    }

    public void setParamType(Class[] paramType) {
        this.paramType = paramType;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Invoke{" +
                "className='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramType=" + Arrays.toString(paramType) +
                ", param=" + Arrays.toString(param) +
                '}';
    }
}

