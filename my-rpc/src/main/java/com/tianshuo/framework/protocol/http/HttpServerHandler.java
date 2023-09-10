package com.tianshuo.framework.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.registry.LocalRegistry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServerHandler {

    public void doHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletInputStream inputStream = request.getInputStream();
//            byte[] bytes = new byte[inputStream.available()];
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            int n = 0;
//            while (-1 != (n = inputStream.read(bytes))) {
//                output.write(bytes, 0, n);
//            }
            Invoke invoke = JSONObject.parseObject(inputStream, Invoke.class);
            Class hClass = LocalRegistry.get(invoke.getInterfaceName());
            Method method = hClass.getMethod(invoke.getMethodName(), invoke.getParamType());
            Object result = method.invoke(hClass.newInstance(), invoke.getParam());
            if (method.getReturnType().equals(Void.class)){
                return;
            }else{
                response.setContentType("text/html;charset=UTF-8");
                request.setCharacterEncoding("utf-8");
                PrintWriter out = response.getWriter();
                out.println((String) result);
            }


        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
