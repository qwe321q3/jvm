package com.tianshuo.framework.protocol;

/**
 * 协议接口，用来适配tomcat，netty等服务启动
 * @author tianshuo
 */
public interface Protocol {
    /**
     * 启动服务
     */
    void startup();
}
