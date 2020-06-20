package com.jvm.banarycode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * jvm 异常处理
 *
 * -XX:+TraceClassLoading  开启类加载跟踪
 * 执行类的main方法时，jvm也会加载这个类
 *
 */

public class JavaException {

    public JavaException() {
        System.out.println("构造方法");
    }

    public void test() throws FileNotFoundException,IOException{


        try {
            FileInputStream fileInputStream = new FileInputStream("d:/tst");
            ServerSocket serverSocket = new ServerSocket(9999);
            serverSocket.accept();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("处理异常!");
        }

    }

    public static void main(String[] args) {
        System.out.println(MyTest8.a);
    }
}
