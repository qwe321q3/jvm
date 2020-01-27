package com.jvm.classloader;

public class CustomerClassLoader {
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		Class<?> clazz12 = Class.forName("com.jvm.classloader.C");
		System.out.println(clazz12.getClassLoader());
		System.out.println(clazz12.hashCode());
		Object o = clazz12.newInstance();
		System.out.println(o.hashCode());

	}
}

class C{
	
}
