package com.tianshuo.framework.protocol.http;

import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.Protocol;

/**
 * @ClassName : HttpServer
 * @Description : 启动服务 (启动netty，tomcat，或者jetty服务等)
 * @Author : tianshuo
 * @Date: 2021-01-29 14:31
 */
public class HttpServer implements Protocol {

    @Override
    public void startup() {

    }

    @Override
    public Object send(Invoke invoke) {
        return null;
    }
}

