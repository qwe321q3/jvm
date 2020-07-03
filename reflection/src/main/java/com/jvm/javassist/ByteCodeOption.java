package com.jvm.javassist;

import javassist.*;

public class ByteCodeOption {


    public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {

        ServiceTest serviceTest = createProxy();
        serviceTest.sayHello();

    }


    private static ServiceTest createProxy() throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException {
        ClassPool classPool = new ClassPool();
        classPool.appendSystemPath();
        // 1. 创建一个空类
        CtClass proxyImpl = classPool.makeClass("$proxy");
        // 2. 获取的接口

        proxyImpl.addInterface(classPool.get(ServiceTest.class.getName()));

        //返回类型
         CtClass returnType = classPool.get(void.class.getName());
        CtMethod cm = new CtMethod(returnType, "sayHello", null, proxyImpl);
        cm.setBody("{System.out.println(\"hello proxy!!\");}");
        proxyImpl.addMethod(cm);

        //参数
      /*     CtClass[] parameters = new CtClass[]{ classPool.get(String.class.getName()) };
        CtMethod cm2 = new CtMethod(returnType, "sayHello", parameters, proxyImpl);
        cm2.setBody("{System.out.println(\"hello proxy!! $1\");}");
        proxyImpl.addMethod(cm2);*/

        Class<?> aClass = proxyImpl.toClass();
        return (ServiceTest) aClass.newInstance();
    }



}

interface ServiceTest{
    void sayHello();

//    void sayHello(String string);
}