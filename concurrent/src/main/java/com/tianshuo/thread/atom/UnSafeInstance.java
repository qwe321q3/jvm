package com.tianshuo.thread.atom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Classname UnSafeInstance
 * @Description 用于获取的Unsafe类型的实例
 * @Date 2020/8/21 0021 22:16
 * @Created by Administrator
 */
public class UnSafeInstance {

    public static Unsafe getUnSafe() {
        Field singletonInstanceField = null;
        try {
            singletonInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        singletonInstanceField.setAccessible(true);

        Unsafe unsafe = null;
        try {
            unsafe = (Unsafe)singletonInstanceField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }

    public static Unsafe createUnsafe() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
