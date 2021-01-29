package com.tianshuo.framework.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : LocalRegistry
 * @Description : 本地注册
 * @Author : tianshuo
 * @Date: 2021-01-29 14:46
 */
public class LocalRegistry {

    private static Map<String, Class> localRegistry = new HashMap<>();


    public static void register(String serverName, Class clazz) {
        localRegistry.put(serverName, clazz);
    }

    public static Class get(String serverName) {
        return localRegistry.get(serverName);
    }
}

