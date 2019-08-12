package com.jvm;

import sun.reflect.Reflection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class JdbcSPI {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


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


        System.out.println(ClassLoaderPath.a);
        System.out.println(ClassLoaderPath.a);

        new ClassLoaderPath();




    }
}
