package com.jvm.classloader;

/**
 * 如果一个常量的值在编译期间就会确定的话，这个常量的值，就会放入到该常量被调用的方法，所属类的常量池中
 * 如果一个常量在编译期间无法确定时，就不会被放入到调用此常量的方法归属类的常量池中去
 * @author Administrator
 *
 */
public class JvmTest {

	public static void main(String[] args) {
		System.out.println(Parent.STE);
	}
}

class Parent{
	
	public static final String STE ="777";

	static {
		System.out.println("parent static block");
	}

}
