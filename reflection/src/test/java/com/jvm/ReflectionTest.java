package com.jvm;

import com.jvm.annotation.AutoWired;
import com.jvm.controller.UserController;
import com.jvm.model.UserService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class ReflectionTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    /**
     * 使用属性的get和set方法，来注入属性
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Test
    public void reflection() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserController userController = new UserController();

        Class<? extends UserController> clazz = userController.getClass();
        Field userServiceField = clazz.getDeclaredField("userService");
        UserService userService = new UserService();
        String methodName = "set"+userServiceField.getName().substring(0,1).toUpperCase()+userServiceField.getName().substring(1);
        System.out.println(methodName);
        Method declaredMethod = clazz.getDeclaredMethod(methodName, UserService.class);
        declaredMethod.invoke(userController, userService);
        System.out.println(userService);
        System.out.println(userController.getUserService());

    }


    /**
     * 使用属性的get和set方法，来注入属性
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Test
    public void reflectionPrivate() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        UserController userController = new UserController();

        Class<? extends UserController> clazz = userController.getClass();
        Field userServiceField = clazz.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userController,userServiceField.getType().newInstance());
        System.out.println(userController.getUserService());
    }

    /**
     * 通过自定义注解获取对象属性，然后设置私有属性为可见，然后设置对象实例
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void annotationReflection() throws IllegalAccessException, InstantiationException {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field a:fields) {
            if (a.getAnnotation(AutoWired.class) != null) {
                //设置私有属性可见
                a.setAccessible(true);
                a.set(userController,a.getType().newInstance());
            }
        }

        System.out.println(userController.getUserService());
    }





    @Test
    public void hashMapResizeTest() {
        Map<String, Integer> map = new HashMap<>(2);
        map.put("a", 1);
        map.put("b",2);
        map.put("c", 3);
    }

}
