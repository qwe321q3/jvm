package com.tianshuo.framework.registry;

import java.util.HashMap;

/**
 * 保存服务名称与实现类关系
 * @author tianshuo
 */
public interface Registry {

    /**
     * 注册的服务与实现类关系
     * @param serverName
     * @param clazz
     */
    void register(String serverName, Class clazz);

    /**
     * 注册的服务名
     * @param serverName
     * @return
     */
    Class get(String serverName);
}
