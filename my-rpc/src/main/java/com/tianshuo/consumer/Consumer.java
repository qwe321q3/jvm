package com.tianshuo.consumer;

import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.ProxyFactory;
import com.tianshuo.framework.protocol.Protocol;
import com.tianshuo.framework.protocol.netty.NettyClient;
import com.tianshuo.framework.protocol.netty.NettyServer;
import com.tianshuo.provider.IHello;

import java.lang.reflect.Method;

public class Consumer {
    public static void main(String[] args) throws NoSuchMethodException {

       /*
       V1 版本
        Method method = IHello.class.getMethod("sayHello", String.class);
        Invoke invoke = new Invoke();
        invoke.setInterfaceName(IHello.class.getName());
        invoke.setMethodName(method.getName());
        invoke.setParamType(method.getParameterTypes());
        invoke.setParam(new Object[]{"测试"});


        Protocol protocol = new NettyServer("localhost", 8000);
        Object res = protocol.send(invoke);
        System.out.println(res);*/

        //  V2

        IHello proxy = ProxyFactory.getProxy(IHello.class);
        String sayHello = proxy.sayHello("测试一下字");
        System.out.println("返回值为："+sayHello);

    }
}
