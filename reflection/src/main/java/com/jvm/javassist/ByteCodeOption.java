package com.jvm.javassist;

import javassist.*;

/**
 * @author tianshuo
 */
public class ByteCodeOption {


    public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {

        ServiceTest serviceTest = createProxy();
        serviceTest.sayHello("aaa");

    }


    /**
     * 版本1 ，模拟代理
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private static ServiceTest createProxy() throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException {
        ClassPool classPool = new ClassPool(true);
        // 1. 创建一个空类
        CtClass proxyImpl = classPool.makeClass("$proxy");
        // 2. 获取的接口

        proxyImpl.addInterface(classPool.get(ServiceTest.class.getName()));

        //返回类型
        CtClass returnType = classPool.get(void.class.getName());
//        CtMethod sayHello = CtNewMethod.make(returnType, "sayHello", null, null,
//                "{System.out.println(\"hello proxy!!\");}", proxyImpl);
        CtMethod sayHello = new CtMethod(returnType, "sayHello", null, proxyImpl);
        sayHello.setBody("{System.out.println(\"hello proxy!!\");}");
        proxyImpl.addMethod(sayHello);

        //参数
           CtClass[] parameters = new CtClass[]{ classPool.get(String.class.getName()) };
        CtMethod cm2 = new CtMethod(returnType, "sayHello", parameters, proxyImpl);
        cm2.setBody("{System.out.println(\"hello proxy!! \"+$1);}");
        proxyImpl.addMethod(cm2);

        Class<?> aClass = proxyImpl.toClass();
        return (ServiceTest) aClass.newInstance();
    }


    /**
     * 传递要实现的接口类
     * @param cl
     * @param source
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private static ServiceTest createProxyV2(Class<?> cl,String source) throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException {

        return null;
    }



        public interface ServiceTest{
        void sayHello();
        void sayHello(String hello1);
    }


    public interface ServiceTestV2 {
        void sayHello();
        String sayHelloV2(String aa);

    }

}


