package com.jvm.system;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型擦除
 * @author tianshuo
 *
 * 关于泛型变量的使用，是会在编译之前检查的
 *
 * 泛型会在编译期间，类型会擦除， List<Integer> list = new ArrayList(); 其实会变成
 *         List list = new ArrayList<>();
 *         泛型会被擦除，这时候list只是原始类型，即Object类型
 * 使用反射可以加入泛型规定外的数据。
 *
 * 重点：
 * 类型检查就是针对引用的，谁是一个引用，用这个引用调用泛型方法，就会对这个引用调用的方法进行类型检测，而无关它真正引用的对象
 */
public class EliminateGenericTypes {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Integer> list = new ArrayList<>();
        list.add(111);
        List<String> stringList = new ArrayList<>();


        System.out.println(list.getClass() == stringList.getClass());

        list.getClass().getMethod("add",Object.class).invoke(list,"bbb");


        System.out.println(list);

        ArrayList list2 = new ArrayList<String>();
        list2.add("2");
        list2.add(1);
    }

    /**
     * 当静态方法返回值为泛型类型时，要使用<T>
     * 泛型方法使用T 是参数指定的T
     * @param <T>
     * @return
     */
    public static <T> T getBean(T t){
        return null;
    }
}
