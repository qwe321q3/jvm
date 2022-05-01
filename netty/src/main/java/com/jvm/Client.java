package com.jvm;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 不管服务端是用的BIO,NIO或者是AIO，客户端缺不一定非得一一对应
 * 客户端可以都用BIO
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //创建客户端socket
        Socket socket = new Socket("127.0.0.1",8000);

        OutputStream outputStream = socket.getOutputStream();

        PrintStream printStream = new PrintStream(outputStream);

        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("这里说：");
            printStream.println(sc.nextLine());
            printStream.flush();

        }

    }
}
