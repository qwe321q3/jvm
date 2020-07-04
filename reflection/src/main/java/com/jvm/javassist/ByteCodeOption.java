package com.jvm.javassist;

import javassist.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author tianshuo
 */
public class ByteCodeOption {


    public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException, NoSuchFieldException {

//        ServiceTest serviceTest = createProxy();
//        serviceTest.sayHello("aaa");
//
//
//        ServiceTestV2 proxyV2 = createProxyV2(ServiceTestV2.class, "{System.out.println(\"hello proxy \" +$1);" +
//                "return $1;}");
//        String a = proxyV2.sayHelloV2("aa");
//
//        System.out.println(a);


        UserMapper userMapper = createProxy(UserMapper.class, (methodName, args1) -> {

            if ("query".equals(methodName)) {
                return "query: "+args1[0];
            }else if("update".equals(methodName)){
                System.out.println("update: "+args1[0]+ "  - "+args1[1]);
                return null;
            }
            return null;
        });

        String ss = userMapper.query("kkk");
        System.out.println(ss);

        userMapper.update("ddd",34);


    }


    /**
     * 版本1 ，模拟代理 用javassist实现接口
     * 如果有其他接口要用时，就无法满足
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private static ServiceTest createProxy() throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException {
        ClassPool classPool = new ClassPool(true);
        //         //构建ClassPool 设置true为默认的classLoader  APPClassLoader
        CtClass proxyImpl = classPool.makeClass("$proxy");
        // 实现的ServiceTest接口
        proxyImpl.addInterface(classPool.get(ServiceTest.class.getName()));

        //定义Javassist支持的返回值类型
        CtClass returnType = classPool.get(void.class.getName());
//        CtMethod sayHello = CtNewMethod.make(returnType, "sayHello", null, null,
//                "{System.out.println(\"hello proxy!!\");}", proxyImpl);
        //创建接口中的sayHello方法
        CtMethod sayHello = new CtMethod(returnType, "sayHello", null, proxyImpl);
        //设置sayHello方法的具体实现代码
        sayHello.setBody("{System.out.println(\"hello proxy!!\");}");
        //将sayHello方法加入到生成的代理类中
        proxyImpl.addMethod(sayHello);

        //参数
        CtClass[] parameters = new CtClass[]{classPool.get(String.class.getName())};
        CtMethod cm2 = new CtMethod(returnType, "sayHello", parameters, proxyImpl);
        cm2.setBody("{System.out.println(\"hello proxy!! \"+$1);}");

        proxyImpl.addMethod(cm2);

        Class<?> aClass = classPool.toClass(proxyImpl);
        return (ServiceTest) aClass.newInstance();
    }


    /**
     * 传递接口类，就方法实现
     * 缺点： 方法是固定的，另外方法的实现要从外部传递进来的，javassist语法也需要以一定的基础
     *
     * @param cl     接口类
     * @param source 方法实现
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private static <T> T createProxyV2(Class<T> cl, String source) throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException {
        //构建ClassPool 设置true为默认的classLoader  APPClassLoader
        ClassPool classPool = new ClassPool(true);
        //构建代理类
        CtClass $proxy0 = classPool.makeClass("$proxy0");
        //实现的接口
        $proxy0.addInterface(classPool.get(cl.getName()));

        CtClass returnType = classPool.get(String.class.getName());
        CtClass[] paramter = {classPool.get(String.class.getName())};

        CtMethod sayHello = CtNewMethod.make(returnType, "sayHelloV2", paramter, null, source, $proxy0);

        $proxy0.addMethod(sayHello);

        Class<?> aClass = classPool.toClass($proxy0);


        return (T) aClass.newInstance();
    }

    static int index = 1;

    /**
     * 动态代理，能实现的任何接口及接口里边的方法
     *
     * 模拟jdk动态代理
     *
     * @param interfaceClass      需要被代理的接口类
     * @param invokecationHandler 方法实现  用来执行业务方法实现
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private static <T> T createProxy(Class<T> interfaceClass, InvokecationHandler invokecationHandler) throws IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException, NoSuchFieldException {
        ClassPool classPool = new ClassPool(true);

        //1、生成空的代理类
        CtClass proxyClass = classPool.makeClass("$proxy" + index);


        //2、给proxyClass增加的InvokecationHandler的属性，用于方便后边调用的实现。

        String src = "private com.jvm.javassist.ByteCodeOption.InvokecationHandler h;";
        CtField invokecationHandlerField = CtField.make(src, proxyClass);

        proxyClass.addField(invokecationHandlerField);

        //3、让代理类proxyClass实现的传递进来的接口
        //-- 接口转为的Javassist支持的接口
        CtClass proxyInterface = classPool.makeClass(interfaceClass.getName());

        //实现接口
        proxyClass.addInterface(proxyInterface);

        //-- 实现接口方法
        Method[] methods = interfaceClass.getMethods();


        String voidType = "h.invoke(\"%s\",$args);";

        String returnType = "return ($r)h.invoke(\"%s\",$args);";


        for (Method method : methods) {

            String methodSrc;

            CtClass returnTypeClass = classPool.get(method.getReturnType().getName());

            if (method.getReturnType().equals(Void.class)) {
                methodSrc = voidType;
            } else {
                methodSrc = returnType;
            }

            methodSrc = String.format(methodSrc, method.getName());

//            System.out.println(methodSrc);

            CtMethod ctMethod = CtNewMethod.make(returnTypeClass, method.getName()
                    , toCtClass(classPool, method.getParameterTypes())
                    , toCtClass(classPool, method.getExceptionTypes())
                    , methodSrc, proxyClass);



            proxyClass.addMethod(ctMethod);

        }


        Class<?> aClass = classPool.toClass(proxyClass);
        Object o = aClass.newInstance();
        Field h = aClass.getDeclaredField("h");
        h.setAccessible(true);
        h.set(o, invokecationHandler);

        return (T) o;

    }

    /**
     * 用于把参数数组和异常数组转换为的javassist对应的CtClass[]
     *
     * @param types
     * @return
     */
    private static CtClass[] toCtClass(ClassPool pool, Class[] types) throws NotFoundException {


        CtClass[] ctClasses = new CtClass[types.length];

        for (int i = 0; i < types.length; i++) {

            Class type = types[i];

            CtClass ctClass = pool.get(type.getName());

            ctClasses[i] = ctClass;
        }

        return ctClasses;

    }


    public interface ServiceTest {
        void sayHello();

        void sayHello(String hello1);
    }


    public interface ServiceTestV2 {
        //        void sayHello();
        String sayHelloV2(String aa);

    }


    public interface ServiceTestV3 {
        String sayHelloV3(String aa);

        void sayHelloV3(String name, int age);


    }


    public interface UserMapper{
        String query(String name);

        void update(String ccc,int page);
    }


    /**
     * 此接口用来让需要代理的人，来实现代理接口的方法实现
     * <p>
     * 因为接口中有很多方法，需要一般方法的实现者都需要，调用人传递
     * 过来的方法名称来做对应方法的处理。
     * <p>
     * 接口方法，会有2个参数
     * 1、methodName 用来告诉实现着方法的名称
     * 2、args  告诉实现者方法的参数
     */
    public interface InvokecationHandler {

        /**
         * 定义接口，让需要生成接口代理的人，来实现具体接口方法
         *
         * @param methodName 方法名称
         * @param args       方法参数
         */
        Object invoke(String methodName, Object[] args);
    }

}



