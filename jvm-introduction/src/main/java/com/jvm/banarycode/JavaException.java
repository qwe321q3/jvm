package com.jvm.banarycode;

import com.mysql.fabric.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * jvm 异常处理
 *
 */

public class JavaException {


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
}
