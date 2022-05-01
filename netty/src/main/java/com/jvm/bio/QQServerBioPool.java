package com.jvm.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 实例为多线程BIO
 * bio多线程
 * 问题：每个客户端对应一个服务线程，会导致线程资源浪费，100W个客户端，如果只有20W的发送消息，但是
 * 服务却还是要开100W个线程
 * C10K 问题1W个线程，先生上下文切换消耗大，系统崩溃等
 *
 * 使用线程池解决C10k问题,但是性能会有影响,最大并发数,为线程池的线程数量
 *
 * BIO单线程：
 * 1、解决不了并发问题。
 * 2、只能有一个客户端能连接上
 */
public class QQServerBioPool {

    public static void main(String[] args) throws IOException {



        //获取Cpu逻辑线程数
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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
            executorService.execute(
                    new Thread(() -> {
                        processSocket(finalClientSocket);
                    })
            );

        }


    }

    private static void processSocket(Socket socket) {

        System.out.println(Thread.currentThread().getName() + " connection successed");
        System.out.println(Thread.currentThread().getName() + " wait data");
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            //把字节流 转换成 字符流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            //把字符流转换成 缓冲区字符流

            BufferedReader br = new BufferedReader(inputStreamReader);

            //开始读取内容
            String msg ;
            while ((msg = br.readLine()) != null){
                System.out.println("读取内容为：" + msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
