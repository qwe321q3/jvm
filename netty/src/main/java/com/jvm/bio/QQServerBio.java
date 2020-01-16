package com.jvm.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 实例为多线程BIO
 * bio多线程
 * 问题：每个客户端对应一个服务线程，会导致线程资源浪费，100W个客户端，如果只有20W的发送消息，但是
 * 服务却还是要开100W个线程
 *
 * BIO单线程：
 * 1、解决不了并发问题。
 * 2、只能有一个客户端能连接上
 */
public class QQServerBio {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("wait connection");

        while (true) {
            //
            //阻塞
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Socket finalClientSocket = clientSocket;
            new Thread(() -> {

                System.out.println(Thread.currentThread().getName() + " connection successed");
                System.out.println(Thread.currentThread().getName() + " wait data");
                //阻塞
                InputStream in = null;
                try {
                    in = finalClientSocket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] bytes = new byte[1024];
                try {
                    while(in.read()!=-1) {
                        in.read(bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print(Thread.currentThread().getName() + " read data ");
                System.out.println(Thread.currentThread().getName() + "  -- " + new String(bytes));
            }).start();
        }


    }
}
