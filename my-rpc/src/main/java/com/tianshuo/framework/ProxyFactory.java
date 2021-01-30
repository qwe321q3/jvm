package com.tianshuo.framework;


import com.tianshuo.framework.protocol.Protocol;
import com.tianshuo.framework.protocol.http.HttpServer;
import com.tianshuo.framework.protocol.netty.NettyServer;
import io.netty.handler.codec.http2.Http2PushPromiseFrame;
import sun.misc.ProxyGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {
    public static <T> T getProxy(Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Invoke invoke = new Invoke();
                invoke.setInterfaceName(interfaceClass.getName());
                invoke.setMethodName(method.getName());
                invoke.setParamType(method.getParameterTypes());
                invoke.setParam(args);


                Protocol protocol = new HttpServer("localhost",8000);
                return protocol.send(invoke);
            }
        });
    }
}
