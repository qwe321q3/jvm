package com.tianshuo.provider;

import com.tianshuo.framework.protocol.Protocol;
import com.tianshuo.framework.protocol.http.HttpServer;
import com.tianshuo.framework.protocol.netty.NettyServer;
import com.tianshuo.framework.registry.LocalRegistry;

/**
 * @ClassName : Pmain
 * @Description : 服务启动类
 * @Author : tianshuo
 * @Date: 2021-01-29 17:10
 */
public class Pmain {
    public static void main(String[] args) {

        LocalRegistry.register(IHello.class.getName(),HelloImpl.class);
        Protocol nettyServer = new NettyServer("localhost",8000);
        nettyServer.startup();


    }
}

