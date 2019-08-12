package com.jvm.banarycode;


/**
 *   方法静态分派：
 *
 *  Grandpa g1 = new Father();
 *  以上代码g1的静态类型是Grandpa，实际类型指向的是Father
 *  变量类型是不会变化的，变量实际类型是可以发生变化的（多态的一种表现）。变量的实际类型是运行期间方可确定
 *  方法重载是一种静态的行为，这种静态行为在编译器就可以完全确定的
 */
public class XuTest {


    //重载是由方法的类型和个数共同决定的

    public void test(Grandpa grandpa){
        System.out.println("gredpa !!");
    }

    public void test(Father father){
        System.out.println("father!!");
    }


    public void test(Son son){
        System.out.println("son !!");
    }

    public static void main(String[] args) {

        Grandpa g1 = new Father();

        Grandpa g2 = new Son();


        XuTest xuTest = new XuTest();

        xuTest.test(g1);

        xuTest.test(g2);


    }


}

class Grandpa {

}

class Father extends Grandpa {

}

class Son extends Father{

}
