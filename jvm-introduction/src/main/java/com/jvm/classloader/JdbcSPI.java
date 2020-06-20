package com.jvm.classloader;

import java.sql.SQLException;

public class JdbcSPI {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ClassLoaderPath classLoaderPath = new ClassLoaderPath();
        System.out.println(classLoaderPath.getClass().getClassLoader());

//        ServiceLoader<Driver> driverServiceLoader = ServiceLoader.load(Driver.class);
//        Iterator<Driver> iterator = driverServiceLoader.iterator();
//
//        while(iterator.hasNext()){ 
//            Driver driver = iterator.next();
//            System.out.println("driver: "+driver.getClass()+" loader: "+driver.getClass().getClassLoader());
//        }
//
//
//        System.out.println(ClassLoaderPath.class.getClassLoader());
//        ClassLoaderPath classLoaderPath = new ClassLoaderPath();
//        System.out.println(classLoaderPath.getClass().getClassLoader());
    }
}
