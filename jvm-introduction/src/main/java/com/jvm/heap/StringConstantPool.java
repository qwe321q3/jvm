package com.jvm.heap;

/**
 * 字符串池
 *
 * 符号引用： String 对应的符号应用 Ljava.lang.String  UTF8   st
 * 1、类和接口的全限定名
 * 2、字段的名称和描述符
 * 3、方法的名称和描述符
 *
 * 字面量：String st = "ss";    ss就是字面量
 *
 *
 * 总结：String.intern()方法，会去字符串常量池(字符串常量池在heap中)中找(equals)对应的字符串，如果找到了，直接返回这个字符串，
 * 如果没有找到，则会返回字符串在堆中的引用。
 *
 * @author tianshuo
 */
public class StringConstantPool {

    public static void main(String[] args) {

        // new String()除了会往堆中放zhuge的字符串，同时还是在字符串常量池（堆中）中放入的zhuge这个字符串
        String s1 = new String("zhuge");
        String s2 = s1.intern();

        // false s1为对象地址,s2为zhuge 所以false
        System.out.println(s1 == s2);
        // true
        System.out.println(s1.equals(s2));
        //常用a,b,c等是有可能之前就存在在字符串常量池中的
        //hellowyyy这个字符串在中字符串常量池中没有找到时，会直接返回了堆中hellowyyy的引用
        String a1 = new String("hellow")+new String("yyy");
        //如果执行此方法时候，就不再a4!=a1 ，执行代码之后，字符串常量池就有了hellowyyy字符串
        // 此时a4就是字符串常量池中的hellowyyy字符串了
//        String a2 = "hellowyyy";
        String a4 = a1.intern();

        //false
//        System.out.println(a2 == a1);

        //true 此时a4 == a1 == a1的堆内存地址
        System.out.println(a4 == a1);


    }


}
